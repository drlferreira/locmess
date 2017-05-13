package ist.meic.cmu.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Diogo on 13/05/2017.
 */
public class Packet implements Serializable {

    private Location location;
    private List<Message> notifications;


    public Packet(Location location, List<Message> notifications) {
        this.location = location;
        this.notifications = notifications;
    }

    public Packet() {
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Message> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Message> notifications) {
        this.notifications = notifications;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Packet packet = (Packet) o;

        return this.getLocation().equals(packet.getLocation());
    }


    @Override
    public String toString() {
        return "Packet{" +
                "location=" + location +
                ", notifications=" + notifications +
                '}';
    }
}
