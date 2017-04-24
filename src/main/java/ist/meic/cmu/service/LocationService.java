package ist.meic.cmu.service;

import ist.meic.cmu.domain.Location;
import ist.meic.cmu.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Diogo on 05/04/2017.
 */

@Service
public class LocationService {

    private List<Location> locations;

    @Autowired
    TokenService tokenService;

    @Autowired
    LocationRepository locationRepository;

    @PostConstruct
    private void init(){
        locations = locationRepository.findAll();
    }

    public List<Location> listLocations() {
        return locations;
    }

    public void createLocation(Location location) {
        locations.add(location);
        locationRepository.saveAndFlush(location);
    }

    public void removeLocation(Location location) {
        locations.remove(location);
        locationRepository.delete(location);
    }
}
