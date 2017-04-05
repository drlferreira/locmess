package ist.meic.cnv.controller;

import ist.meic.cnv.domain.Location;
import ist.meic.cnv.domain.Pair;
import ist.meic.cnv.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Diogo on 05/04/2017.
 */

@RestController
public class LocationController {

    @Autowired
    LocationService locationService;

    @RequestMapping(method = RequestMethod.GET, value = "location/{token}/list")
    public List<Location> listLocations(@PathVariable String token) {
        return locationService.listLocations(token);
    }

    @RequestMapping(method = RequestMethod.POST, value = "location/{token}/create")
    public void createLocation(@PathVariable String token, @RequestBody Location location) {
        locationService.createLocation(token, location);
    }

    @RequestMapping(method = RequestMethod.POST, value = "location/{token}/remove")
    public void removeLocation(@PathVariable String token, @RequestBody Location location) {
        locationService.removeLocation(token, location);
    }

}
