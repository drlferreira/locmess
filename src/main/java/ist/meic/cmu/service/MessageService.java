package ist.meic.cmu.service;

import ist.meic.cmu.domain.*;
import ist.meic.cmu.dto.MessageDto;
import ist.meic.cmu.repository.MessageRepository;
import ist.meic.cmu.repository.NotificationRepository;
import ist.meic.cmu.repository.UserRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jp_s on 4/10/2017.
 */
@Service
public class MessageService {

    private final long DELAY = 0;
    private final long PERIOD = 15000;

    private static final String WHITELIST = "W";
    private static final String BACKLIST = "B";

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    TrackerService trackerService;

    private List<Notification> notifications;
    private ConcurrentHashMap<String,List<Notification>> queue;

    @PostConstruct
    private void init() {
        notifications = notificationRepository.findAll();
        queue = new ConcurrentHashMap<>();
        // invalidate old notifications
        /*new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                getValidNotifications(notifications);
            }
        },DELAY,PERIOD);*/
    }

    public void postMessage(String token, Message message){
        User user = userRepository.findUserByUsername(tokenService.getUsername(token));
        message.setOwner(user.getUsername());
        user.getMessages().add(message);
        messageRepository.saveAndFlush(message);
        userRepository.saveAndFlush(user);
        Notification notification = new Notification(message.getId(), message);
        notificationRepository.saveAndFlush(notification);
        notifications.add(notification);
        propagate(message);
    }

    public void unpostMessage(String token, MessageDto message) throws ServletException {
        User user = userRepository.findUserByUsername(tokenService.getUsername(token));
        if(message.getPublisher().equals(user.getUsername())){
            for (Message m : user.getMessages()){
                if(message.getId().equals(m.getId()))
                    scheduleRemoval(message.getId(),user.getUsername());
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

    private void scheduleRemoval(int id, String username){
        User user = userRepository.findUserByUsername(username);
        user.getMessages().remove(messageRepository.findOne(id));
        userRepository.saveAndFlush(user);
        messageRepository.delete(id);
        notificationRepository.delete(id);
        // since the equals is based on id, its irrelevant the message
        notifications.remove(new Notification(id,null));
    }

    private void getValidNotifications(List<Notification> notifications){
        List<Notification> toRemove = new ArrayList<>();
        for (Notification notification : notifications){
            // the notification has expired!
            if(new Date().after(notification.getMessage().getEndDate()))
                scheduleRemoval(notification.getId(), notification.getMessage().getOwner());
            toRemove.add(notification);
        }
        notifications.removeAll(toRemove);
    }

    private void propagate(Message message){
        for (String username : trackerService.getActiveUsers()){
            System.out.println("pty");
            System.out.println(message.getLocation());
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

            queue.get(username).add(new Notification(message.getId(), message));
        }
    }

    public List<MessageDto> getNotifications(String username) {
        getValidNotifications(queue.get(username));
        ArrayList<Message> messages = new ArrayList<>();
        for (Notification notification : notifications)
            messages.add(notification.getMessage());
        return parseMessage(messages);
    }

    public void removeNotification(String username, Integer id) {
        Notification toRemove = null;
        for (Notification notification : queue.get(username)){
            if(notification.getId() == id)
                toRemove = notification;
        }
        if(toRemove != null) queue.get(username).remove(toRemove);
    }

    public void initializeNotifications(User user){
        List<Message> messages = user.getMessages();
        if(notifications.size() == 0 && messages.size() == 0){
            System.out.println("pttt");
            queue.put(user.getUsername(), new ArrayList<>());
        }
        else if(messages.size() == 0){
            System.out.println("espan");
            for (Notification notification : notifications){
                propagate(notification.getMessage());
            }
        }
        else {
            System.out.println("tjtj");
            for (Message message : messages) {
                // avoid notifications that are hold
                if (!new Date().after(message.getEndDate())) {
                    for (Notification notification : notifications) {
                        // all the messages that the user doesn't know about
                        if (!message.getId().equals(notification.getMessage().getId()))
                            propagate(message);
                    }
                }
            }
        }
    }

}
