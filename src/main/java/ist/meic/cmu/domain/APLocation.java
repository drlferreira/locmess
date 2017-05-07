package ist.meic.cmu.domain;

import javax.persistence.Entity;

/**
 * Created by Diogo on 08/04/2017.
 */
@Entity
public class APLocation extends Location {

    private List<String> WifiLocations;

    public APLocation(){
    }

    public APLocation(String name, List<String> wifiLocations){
        super(name);
        this.WifiLocations = wifiLocations;
    }

    public void setWifiLocations(List<String> wifiLocations) {
        WifiLocations = wifiLocations;
    }

    public List<String> getWifiLocations() {

        return WifiLocations;
    }

    @Override
    public boolean equals(Object o){
        if(o != null && o instanceof APLocation){
            APLocation toCompare = (APLocation) o;
            return super.equals(o);
        }
        return false;
    }

}
