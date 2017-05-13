package ist.meic.cmu.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diogo on 05/04/2017.
 */

@Entity
public class User implements Serializable {

    @Id
    private String username;
    private String password;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_PAIR", joinColumns = {@JoinColumn(name = "username")},
            inverseJoinColumns = {@JoinColumn(name = "PAIR_KEY")})
    private List<Pair> pairs = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "USER_MSG", joinColumns = {@JoinColumn(name = "username")},
            inverseJoinColumns = {@JoinColumn(name = "MSG_ID")})
    private List<Message> messages = new ArrayList<>();

    // needed for rest calls
    public User(){
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        pairs = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public List<Pair> getPairs() {
        return pairs;
    }

    public void setPairs(List<Pair> pairs) {
        this.pairs = pairs;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
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

    public String getValue(String key){
        for (Pair pair : pairs){
            if (pair.getKey().equals(key))
                return pair.getValue();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username != null ? username.equals(user.username) : user.username == null;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", pairs=" + pairs +
                ", messages=" + messages +
                '}';
    }
}
