package ist.meic.cmu.service;

import ist.meic.cmu.domain.*;
import ist.meic.cmu.dto.MessageDto;
import ist.meic.cmu.repository.MessageRepository;
import ist.meic.cmu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jp_s on 4/10/2017.
 */
@Service
public class MessageService {

    private static final String WHITELIST = "W";
    private static final String BACKLIST = "B";
    private final long DELAY = 0;
    private final long PERIOD = 30000;
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;


    @Autowired
    TrackerService trackerService;

    private List<Packet> queue;

    @PostConstruct
    private void init() {
        queue = new ArrayList<>();
        removeOldMessages();
        List<Message> messages = messageRepository.findAll();
        for (Message message : messages) {
            Packet toAdd = new Packet(message.getLocation(), new ArrayList<>());
            if (queue.contains(toAdd)) {
                toAdd = findPacket(message);
                toAdd.getNotifications().add(message);
                continue;
            }
            toAdd.getNotifications().add(message);
            queue.add(toAdd);
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                removeOldMessages();
            }
        }, DELAY, PERIOD);
    }

    public void postMessage(String token, Message message){
        User user = userRepository.findUserByUsername(tokenService.getUsername(token));
        message.setOwner(user.getUsername());
        user.getMessages().add(message);
        messageRepository.saveAndFlush(message);
        userRepository.saveAndFlush(user);

        // if the location is not know yet add it to the array
        Packet packet = new Packet(message.getLocation(), new ArrayList<>());
        if (queue.contains(packet)) {
            packet = findPacket(message);
            packet.getNotifications().add(message);
            return;
        }
        packet.getNotifications().add(message);
        queue.add(packet);
    }

    public void unpostMessage(String token, MessageDto message) throws ServletException {
        User user = userRepository.findUserByUsername(tokenService.getUsername(token));
        if(message.getPublisher().equals(user.getUsername())){
            for (Message m : user.getMessages()){
                if (message.getId().equals(m.getId())) {
                    // find packet will never return null, since the repositories are sync
                    scheduleRemoval(user.getUsername(), message.getId(), findPacket(m));
                }
            }
        }
        else
            throw new ServletException("You have no permission to unpost messages that aren't yours!");
    }

    public List<MessageDto> listMessages(String token) {
        return parseMessage(userRepository.findUserByUsername(tokenService.getUsername(token)).getMessages());
    }

    private List<MessageDto> parseMessage(List<Message> messages) {
        List<MessageDto> output = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        for (Message message : messages){
            output.add(new MessageDto(message.getId(), message.getTitle(),
                    message.getContent(), message.getOwner(), simpleDateFormat.format(message.getBeginDate())));
        }
        return output;
    }

    private Message propagate(Message message) {
        Message output = null;
        for (String username : trackerService.getActiveUsers()){
            // ignore the messages if:
            // they are after the end date
            // they are mine - since they are already added
            // the location doesn't match
            // I don't match the policy in whitelist || I match the policy in backlist
            if(new Date().after(message.getEndDate()) || message.getOwner().equals(username))
                continue;
            Location userLocation = trackerService.getUserLocation(username);
            // catches APLocations and spot on GPSLocations, i.e. not considering the radius
            if(!message.getLocation().equals(userLocation))
                continue;

            List<Pair> keys = message.getPairs();
            User user = userRepository.findUserByUsername(username);
            if(message.getPairs().size() != 0) {
                if (message.getPolicy().equals(WHITELIST)) {
                    if (!user.getPairs().containsAll(keys))
                        continue;
                } else if (message.getPolicy().equals(BACKLIST)) {
                    if (user.getPairs().containsAll(keys))
                        continue;
                }
            }
            output = message;
        }
        return output;
    }

    public List<MessageDto> findInterests(String username, Location location) {
        List<Message> output = new ArrayList<>();
        User user = userRepository.findUserByUsername(username);
        for (Packet packet : queue) {
            if (packet.getLocation().equals(location)) {
                for (Message notification : packet.getNotifications()) {
                    if (propagate(notification) != null && !user.getMessages().contains(notification)) {
                        output.add(notification);
                    }
                }
            }
        }
        return parseMessage(output);
    }

    private void removeOldMessages() {
        List<Message> messages = messageRepository.findAll();
        for (Message message : messages) {
            if (new Date().after(message.getEndDate()))
                scheduleRemoval(message.getOwner(), message.getId(), null);
        }
    }

    private void scheduleRemoval(String username, int messageId, Packet packet) {
        User user = userRepository.findUserByUsername(username);
        Message message = messageRepository.findOne(messageId);
        user.getMessages().remove(message);
        userRepository.saveAndFlush(user);
        messageRepository.delete(messageId);
        if (packet != null)
            packet.getNotifications().remove(message);
    }

    private Packet findPacket(Message message) {
        for (Packet packet : queue) {
            if (packet.getLocation().equals(message.getLocation()))
                return packet;
        }
        return null;
    }


}
