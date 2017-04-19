package ist.meic.cmu.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by jp_s on 4/10/2017.
 */

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String username;
    private String content;
    private String location;
    private Date beginDate;
    private Date endDate;
    @ElementCollection
    @CollectionTable(name="contacts")
    private List<Pair> contactInfo;
    @ElementCollection
    @CollectionTable(name="keypairs")
    private List<Pair> pairs;

    public Message() {}

    public Message(int id, String username, String location, Date beginDate, Date endDate, List<Pair> contactInfo, List<Pair> pairs) {
        this.id = id;
        this.username = username;
        this.location = location;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.contactInfo = contactInfo;
        this.pairs = pairs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Pair> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(List<Pair> contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<Pair> getPairs() {
        return pairs;
    }

    public void setPairs(List<Pair> pairs) {
        this.pairs = pairs;
    }
}
