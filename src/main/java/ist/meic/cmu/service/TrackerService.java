package ist.meic.cmu.service;

import ist.meic.cmu.domain.Location;
import ist.meic.cmu.domain.User;
import ist.meic.cmu.dto.MessageDto;
import ist.meic.cmu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Diogo on 08/04/2017.
 */
@Service
public class TrackerService {

    // tolerated delay for the next heartbeat 90 seconds
    private final long HEARTBEAT_DELAY = 190000;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    private ConcurrentHashMap<String, Location> clientsLocations;
    private ConcurrentHashMap<String, Timer> timers;

    @PostConstruct
    public void init(){
        clientsLocations = new ConcurrentHashMap<>();
        timers = new ConcurrentHashMap<>();
    }

    public List<MessageDto> track(String token, Location currentLocation) {
        System.out.println(currentLocation);
        String username = tokenService.getUsername(token);
        clientsLocations.put(username, currentLocation);
        stopTimer(username);
        timers.put(username, missedHeartBeat(token, HEARTBEAT_DELAY));
        return messageService.getNotifications(username);
    }

    private void stopTimer(String username) {
        Timer timer = timers.get(username);
        if(timer != null) {
            timer.cancel();
            timer.purge();
            timers.remove(username);
        }
    }

    // when a user logsout there is no need to track its location anymore
    public void remove(String token) {
        String user = tokenService.getUsername(token);
        if(clientsLocations.keySet().contains(user)){
            clientsLocations.remove(user);
            stopTimer(user);
        }
    }

    private Timer missedHeartBeat(String token, long delay){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(token);
                tokenService.removeToken(token);
            }
        }, delay);
        return timer;
    }

    public Location getUserLocation(String username) {
        return clientsLocations.get(username);
    }

    public ArrayList<String> getActiveUsers(){
        return new ArrayList<>(clientsLocations.keySet());
    }

    public ConcurrentHashMap<String, Location> getClientsLocations() {
        return clientsLocations;
    }

}
