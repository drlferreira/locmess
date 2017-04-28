package ist.meic.cmu.service;

import ist.meic.cmu.domain.Location;
import ist.meic.cmu.domain.User;
import ist.meic.cmu.dto.MessageDto;
import ist.meic.cmu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Diogo on 08/04/2017.
 */
@Service
public class TrackerService {

    // tolerated delay for the next hearbeat 30 seconds
    private final long HEARTBEAT_DELAY = 30000;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    private ConcurrentHashMap<User, Location> clientsLocations;
    private ConcurrentHashMap<User, Timer> timers;

    @PostConstruct
    public void init(){
        clientsLocations = new ConcurrentHashMap<>();
        timers = new ConcurrentHashMap<>();
    }

    public List<MessageDto> track(String token, Location currentLocation) {
        User user = userRepository.findUserByUsername(tokenService.getUsername(token));
        clientsLocations.put(user, currentLocation);
        // stop the old timer
        timers.get(user).cancel();
        // start a new one
        timers.put(user, missedHeartBeat(user, token, HEARTBEAT_DELAY));
        return messageService.getNotifications(user.getUsername());
    }

    // when a user logsout there is no need to track its location anymore
    public void remove(String token) {
        User toRemove = null;
        for (User user : clientsLocations.keySet()){
            if(user.equals(tokenService.getUsername(token))) {
                toRemove = user;
                break;
            }
        }
        if(toRemove != null)
            clientsLocations.remove(toRemove);
    }

    private Timer missedHeartBeat(User user, String token, long delay){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                userService.logout(token);
                clientsLocations.remove(user);
            }
        }, delay);
        return timer;
    }

    public Location getUserLocation(String username) {
        return clientsLocations.get(userRepository.findUserByUsername(username));
    }

    public ArrayList<User> getActiveUsers(){
        ArrayList<User> users = new ArrayList<>();
        for (User user : clientsLocations.keySet()){
            users.add(user);
        }
        return users;
    }

}
