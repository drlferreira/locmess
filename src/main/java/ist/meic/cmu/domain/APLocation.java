package ist.meic.cmu.domain;

import javax.persistence.Entity;

/**
 * Created by Diogo on 08/04/2017.
 */
@Entity
public class APLocation extends Location {

    public APLocation(){
    }

    public APLocation(String name){
        super(name);
    }
}
