package ist.meic.cmu.controller;

import ist.meic.cmu.domain.Location;
import ist.meic.cmu.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public List<Location> listLocations() {
        return locationService.listLocations();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createLocation(@RequestBody Location location) {
        locationService.createLocation(location);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLocation(@RequestBody Location location) {
        locationService.removeLocation(location);
    }

}
