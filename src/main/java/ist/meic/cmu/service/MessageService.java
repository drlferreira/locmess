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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jp_s on 4/10/2017.
 */
@Service
public class MessageService {

    private static final String WHITELIST = "W";
    private static final String BACKLIST = "B";

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TrackerService trackerService;

    private ConcurrentHashMap<String, List<Message>> notifications;

    @PostConstruct
    private void init() {
        notifications = new ConcurrentHashMap<>();
    }

    public void postMessage(String token, Message message){
        User user = userRepository.findUserByUsername(tokenService.getUsername(token));
        message.setOwner(user.getUsername());
        user.getMessages().add(message);
        messageRepository.saveAndFlush(message);
        userRepository.saveAndFlush(user);
        System.out.println("Propagate");
        propagate(message);
    }

    public void unpostMessage(String token, MessageDto message) throws ServletException {
        User user = userRepository.findUserByUsername(tokenService.getUsername(token));
        if(message.getPublisher().equals(user.getUsername())){
            Message toRemove = null;
            for (Message m : user.getMessages()){
                if(message.getId().equals(m.getId()))
                    toRemove = m;
            }
            // remove from the user messages
            user.getMessages().remove(toRemove);
            userRepository.saveAndFlush(user);
            messageRepository.delete(message.getId());
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

    private void propagate(Message message){
        System.out.println(message);
        System.out.println(trackerService.getActiveUsers().size());
        System.out.println("VAMOS PROPAGAR!!!!");
        for (User user : trackerService.getActiveUsers()){
            System.out.println("fdifdhufdhfududfhdfudfhuhdfudfh");
            // ignore the messages if:
            // they are after the end date
            // they are mine - since they are already added
            // the location doesn't match
            // I don't match the policy in whitelist || I match the policy in backlist
            if(new Date().after(message.getEndDate()) || message.getOwner().equals(user.getUsername()))
                continue;
            Location userLocation = trackerService.getUserLocation(user.getUsername());
            // catches APLocations and spot on GPSLocations, i.e. not considering the radius
            if(!userLocation.equals(message.getLocation()))
                continue;
            List<Pair> keys = message.getPairs();

            if(message.getPairs().size() != 0) {
                if (message.getPolicy().equals(WHITELIST)) {
                    if (!user.getPairs().containsAll(keys))
                        continue;
                } else if (message.getPolicy().equals(BACKLIST)) {
                    if (user.getPairs().containsAll(keys))
                        continue;
                }
            }
            notifications.get(user.getUsername()).add(message);
        }
    }

    public List<MessageDto> getNotifications(String username) {
        System.out.println(notifications.size());
        return parseMessage(notifications.get(username));
    }

    public void removeNotification(String username, Integer id) {
        Message toRemove = null;
        for (Message m : notifications.get(username)){
            if(m.getId().equals(id))
                toRemove = m;
        }
        if(toRemove != null) notifications.get(username).remove(toRemove);
    }

    public ConcurrentHashMap<String, List<Message>> getNotifications(){
        return notifications;
    }
}
