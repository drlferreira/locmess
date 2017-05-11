package ist.meic.cmu.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.Serializable;

/**
 * Created by Diogo on 10/05/2017.
 */
@Entity
public class Notification implements Serializable {

    @Id
    private int id;
    @Lob
    private Message message;

    public Notification(){
    }

    public Notification(int id, Message message){
        this.id = id;
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
