package ist.meic.cmu.service;

import ist.meic.cmu.domain.Location;
import ist.meic.cmu.domain.User;
import ist.meic.cmu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

    private ConcurrentHashMap<User, Location> clientsLocations;

    @PostConstruct
    public void init(){
        clientsLocations = new ConcurrentHashMap<>();
    }

    public void track(String token, Location currentLocation) {
        User user = userRepository.findUserByUsername(tokenService.getUsername(token));
        clientsLocations.put(user, currentLocation);
        // TODO: Return the list of messages!
        missedHeartBeat(user, token, HEARTBEAT_DELAY);
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

    private void missedHeartBeat(User user, String token, long delay){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                userService.logout(token);
                clientsLocations.remove(user);
            }
        }, delay);
    }

}
