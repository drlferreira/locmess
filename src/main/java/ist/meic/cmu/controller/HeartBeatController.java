package ist.meic.cmu.controller;

import ist.meic.cmu.domain.Location;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Diogo on 08/04/2017.
 */
@RestController
public class HeartBeatController {

    @RequestMapping(method = RequestMethod.POST, value = "/actions/heartbeat")
    public void heartBeat() {
        // TODO: check the project specification, to see what can we send back to the user!
    }

}
