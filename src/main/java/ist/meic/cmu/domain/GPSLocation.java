package ist.meic.cmu.domain;

import javax.persistence.Entity;

/**
 * Created by Diogo on 05/04/2017.
 */
@Entity
public class GPSLocation extends Location {

    private double latitude;
    private double longitude;
    private float radius;

    public GPSLocation(){
    }

    public GPSLocation(double latitude, double longitude, float radius) {
        super("");
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

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public boolean equals(Object o){
        if(o != null && o instanceof GPSLocation){
            GPSLocation toCompare = (GPSLocation) o;
            double x = Math.abs(this.latitude) - Math.abs(toCompare.getLatitude());
            double y = Math.abs(this.longitude) - Math.abs(toCompare.getLongitude());
            double d = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
            return d <= (radius/2);
        }
        return false;
    }

    @Override
    public String toString() {
        return "GPSLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", radius=" + radius +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
