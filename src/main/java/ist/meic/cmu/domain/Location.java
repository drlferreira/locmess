package ist.meic.cmu.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Diogo on 05/04/2017.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes(
        {@JsonSubTypes.Type(value = GPSLocation.class, name = "GPSLocation"),
        @JsonSubTypes.Type(value = APLocation.class, name = "APLocation") })
public abstract class Location implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    public Location(){
    }

    public Location(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o){
        if(o != null && o instanceof Location){
            Location toCompare = (Location) o;
            return this.getName().equals(toCompare.getName());
        }
        return false;
    }

}
