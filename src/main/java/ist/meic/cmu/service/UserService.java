package ist.meic.cmu.service;

import ist.meic.cmu.domain.Pair;
import ist.meic.cmu.domain.User;
import ist.meic.cmu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Diogo on 05/04/2017.
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    TrackerService trackerService;

    public User signUp(User user) {
        User toRegister = userRepository.findUserByUsername(user.getUsername());
        // there is no user with that username
        if(toRegister == null){
            userRepository.saveAndFlush(new User(user.getUsername(), user.getPassword()));
        }
        return toRegister;
    }

    public String login(User user) {
        User toLogin = userRepository.findUser(user.getUsername(), user.getPassword());
        if(toLogin != null){
            return tokenService.generateToken(user.getUsername());
        }
        return null;
    }

    public String logout(String token) {
        trackerService.remove(token);
        return tokenService.removeToken(token);
    }

    public List<Pair> list(String token) {
        // TODO: 2.1.2 SECTION LOGIC
        return userRepository.findUserByUsername(tokenService.getUsername(token)).getPairs();
    }

    public void addPair(String token, Pair pair) {
        User user = userRepository.findUserByUsername(tokenService.getUsername(token));
        user.getPairs().add(pair);
        userRepository.saveAndFlush(user);
    }

    public void removePair(String token, Pair pair) {
        User user = userRepository.findUserByUsername(tokenService.getUsername(token));
        user.getPairs().remove(pair);
        userRepository.saveAndFlush(user);
    }
}
