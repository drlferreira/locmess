package ist.meic.cnv.controller;

import ist.meic.cnv.domain.Pair;
import ist.meic.cnv.domain.User;
import ist.meic.cnv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diogo on 05/04/2017.
 */

@RestController
@RequestMapping(value = "user")
public class UserController extends LocmessController{

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity signUp(@RequestBody User user) {
        User toRegister = userService.signUp(user);
        return toRegister != null ?
                new ResponseEntity<>(
                        buildResponseBody("The username " + user.getUsername() + " is already taken!"),
                        HttpStatus.CONFLICT) :
                new ResponseEntity<>(
                        buildResponseBody("Success"),
                        HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity login(@RequestBody User user) {
        String token = userService.login(user);
        return token == null ?
                new ResponseEntity<>(
                        buildResponseBody("Incorrect credentials!"),
                        HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(buildResponseBodyJson(
                        new ArrayList<String>(){{add("token");}},
                        new ArrayList<String>(){{add(token);}}),
                        HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/logout")
    public void logout(HttpServletRequest httpServletRequest) {
        userService.logout(httpServletRequest.getHeader(TOKEN_HEADER));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/listpairs")
    public List<Pair> list(HttpServletRequest httpServletRequest) {
        return userService.list(httpServletRequest.getHeader(TOKEN_HEADER));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addpair")
    public void addPair(HttpServletRequest httpServletRequest, @RequestBody Pair pair) {
        userService.addPair(httpServletRequest.getHeader(TOKEN_HEADER), pair);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/removepair")
    public void removePair(HttpServletRequest httpServletRequest, @RequestBody Pair pair) {
        userService.removePair(httpServletRequest.getHeader(TOKEN_HEADER), pair);
    }

}
