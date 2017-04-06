package ist.meic.cnv.domain;

import javax.persistence.*;

/**
 * Created by Diogo on 05/04/2017.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Location {

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
}
