package ist.meic.cmu.controller;

import ist.meic.cmu.domain.Location;
import ist.meic.cmu.dto.MessageDto;
import ist.meic.cmu.service.TrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Diogo on 08/04/2017.
 */
@RestController
public class HeartBeatController extends LocmessController {

    @Autowired
    TrackerService trackerService;

    @RequestMapping(method = RequestMethod.POST, value = "/actions/heartbeat")
    public List<MessageDto> heartBeat(HttpServletRequest httpServletRequest, @RequestBody Location currentLocation) {
        return trackerService.track(httpServletRequest.getHeader(TOKEN_HEADER), currentLocation);
    }

}
