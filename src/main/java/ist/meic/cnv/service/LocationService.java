package ist.meic.cnv.service;

import ist.meic.cnv.domain.Location;
import ist.meic.cnv.repository.LocationRepository;
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

    public List<Location> listLocations(String token) {
        return tokenService.getUsername(token) != null ? locations : null;
    }

    public void createLocation(String token, Location location) {
        if(tokenService.getUsername(token) != null){
            locationRepository.saveAndFlush(location);
        }
    }

    public void removeLocation(String token, Location location) {
        if(tokenService.getUsername(token) != null){
            locations.remove(location);
            locationRepository.delete(location);
        }
    }
}
