package ist.meic.cmu.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String title;
    private Location location;
    private String policy;
    @DateTimeFormat(pattern = "MMM d, yyyy HH:mm:ss aaa")
    private Date beginDate;
    @DateTimeFormat(pattern = "MMM d, yyyy HH:mm:ss aaa")
    private Date endDate;
    private String owner;
    private String content;
    @ElementCollection
    @CollectionTable(name="pairs")
    private List<Pair> pairs;

    public Message(Integer id, String title, Location location, String policy, List<Pair> keys, Date beginDate, Date endDate, String content) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.policy = policy;
        this.pairs = keys;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.content = content;
    }

    public Message(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public List<Pair> getPairs() {
        return pairs;
    }

    public void setPairs(List<Pair> pairs) {
        this.pairs = pairs;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if(o != null && o instanceof Message){
            Message toCompare = (Message) o;
            return id.equals(toCompare.getId()) &&
                    location.getId() == toCompare.getLocation().getId() &&
                    policy.equals(toCompare.getPolicy()) &&
                    pairs.containsAll(toCompare.getPairs()) &&
                    beginDate.equals(toCompare.getBeginDate()) &&
                    endDate.equals(toCompare.getEndDate()) &&
                    owner.equals(toCompare.getOwner()) &&
                    content.equals(toCompare.getContent());
        }
        return false;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
