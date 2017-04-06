package ist.meic.cnv.domain;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by Diogo on 05/04/2017.
 */
@Entity
public class GPSLocation extends Location {

    private double latitude;
    private double longitude;
    private double radius;

    public GPSLocation(){
    }

    public GPSLocation(String name, double latitude, double longitude, double radius) {
        super(name);
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
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

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
