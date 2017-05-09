package ist.meic.cmu.domain;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

/**
 * Created by Diogo on 08/04/2017.
 */
@Entity
public class APLocation extends Location {

    @ElementCollection
    @CollectionTable(name="list_aps")
    private List<String> aps;

    public APLocation(){
    }

    public APLocation(String name, List<String> aps){
        super(name);
        this.aps = aps;
    }

    @Override
    public boolean equals(Object o){
        if(o != null && o instanceof APLocation){
            APLocation toCompare = (APLocation) o;
            return aps.containsAll(toCompare.getAps());
        }
        return false;
    }

    public List<String> getAps() {
        return aps;
    }

    public void setAps(List<String> aps) {
        this.aps = aps;
    }

    @Override
    public String toString() {
        return "APLocation{" +
                "aps=" + aps +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
