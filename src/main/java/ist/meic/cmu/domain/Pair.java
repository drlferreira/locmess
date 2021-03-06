package ist.meic.cmu.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Diogo on 05/04/2017.
 */

@Entity
public class Pair implements Serializable {

    @Id
    @Column(name = "PAIR_KEY")
    private String key;
    private String value;

    public Pair(){
    }

    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o){
        if(o != null && o instanceof Pair){
            Pair toCompare = (Pair) o;
            return this.getKey().equals(toCompare.getKey()) && this.getValue().equals(toCompare.getValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

}
