package ist.meic.cmu.controller;

import ist.meic.cmu.domain.Location;
import ist.meic.cmu.service.TrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Diogo on 08/04/2017.
 */
@RestController
public class HeartBeatController extends LocmessController {

    @Autowired
    TrackerService trackerService;

    @RequestMapping(method = RequestMethod.POST, value = "/actions/heartbeat")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void heartBeat(HttpServletRequest httpServletRequest, @RequestBody Location currentLocation) {
        // TODO: check the project specification, to see what can we send back to the user!
        trackerService.track(httpServletRequest.getHeader(TOKEN_HEADER), currentLocation);
    }

}
