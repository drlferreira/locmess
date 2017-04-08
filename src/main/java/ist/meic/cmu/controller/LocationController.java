package ist.meic.cmu.controller;

import ist.meic.cmu.domain.Location;
import ist.meic.cmu.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Diogo on 05/04/2017.
 */
@RestController
@RequestMapping(value = "location")
public class LocationController extends LocmessController {

    @Autowired
    LocationService locationService;

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public List<Location> listLocations(HttpServletRequest httpServletRequest) {
        return locationService.listLocations(httpServletRequest.getHeader(TOKEN_HEADER));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public void createLocation(HttpServletRequest httpServletRequest, @RequestBody Location location) {
        locationService.createLocation(httpServletRequest.getHeader(TOKEN_HEADER), location);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/remove")
    public void removeLocation(HttpServletRequest httpServletRequest, @RequestBody Location location) {
        locationService.removeLocation(httpServletRequest.getHeader(TOKEN_HEADER), location);
    }

}
