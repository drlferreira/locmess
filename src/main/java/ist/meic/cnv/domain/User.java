package ist.meic.cnv.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diogo on 05/04/2017.
 */

@Entity
public class User {

    @Id
    private String username;
    private String password;
    @Lob
    private List<Pair> pairs;

    // needed for rest calls
    public User(){
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        pairs = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addPair(Pair pair){
        pairs.add(pair);
    }

    public String getValue(String key){
        for (Pair pair : pairs){
            if (pair.getKey().equals(key))
                return pair.getValue();
        }
        return null;
    }

    public List<Pair> getPairs(){
        return pairs;
    }

    public void removePair(Pair pair){
        pairs.remove(pair);
    }

}
