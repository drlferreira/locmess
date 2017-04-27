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

    @Override
    public boolean equals(Object o){
        if(o != null && o instanceof APLocation){
            APLocation toCompare = (APLocation) o;
            return super.equals(o);
        }
        return false;
    }

}
