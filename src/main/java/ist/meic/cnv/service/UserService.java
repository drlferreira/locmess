package ist.meic.cnv.service;

import ist.meic.cnv.domain.User;
import ist.meic.cnv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Diogo on 05/04/2017.
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

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
        return tokenService.removeToken(token);
    }
}
