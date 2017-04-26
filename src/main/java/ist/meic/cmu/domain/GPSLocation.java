package ist.meic.cmu.domain;

import javax.persistence.Entity;

/**
 * Created by Diogo on 05/04/2017.
 */
@Entity
public class GPSLocation extends Location {

    private double latitude;
    private double longitude;

    public GPSLocation(){
    }

    public GPSLocation(double latitude, double longitude) {
        super("");
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
