package ist.meic.cnv.controller;

import ist.meic.cnv.domain.User;
import ist.meic.cnv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by Diogo on 05/04/2017.
 */

@RestController
public class UserController extends LocmessController{

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "user/signup")
    public ResponseEntity signUp(@RequestBody User user) {
        User toRegister = userService.signUp(user);
        return toRegister != null ?
                new ResponseEntity<>(
                        buildResponseBody("The username " + user.getUsername() + " is already taken!"),
                        HttpStatus.CONFLICT) :
                new ResponseEntity<>(
                        buildResponseBody(""),
                        HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "user/login")
    public ResponseEntity login(@RequestBody User user) {
        String token = userService.login(user);
        return token == null ?
                new ResponseEntity<>(
                        buildResponseBody("The username " + user.getUsername() + " doesn't exist!"),
                        HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(buildResponseBodyJson(
                        new ArrayList<String>(){{add("token");}},
                        new ArrayList<String>(){{add(token);}}),
                        HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "user/logout")
    public ResponseEntity login(String token) {
        return userService.logout(token) == null ?
                new ResponseEntity<>(
                    buildResponseBody("Logout failed!"),
                    HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(
                        buildResponseBody(""),
                        HttpStatus.OK);
    }


}
