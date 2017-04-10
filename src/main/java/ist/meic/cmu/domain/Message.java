package ist.meic.cmu.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jp_s on 4/10/2017.
 */

@Entity
public class Message {

    @Id
    private String PublisherUsername;
    private String Content;
    private Location location;
    private LocalDateTime date;
    @ElementCollection
    @CollectionTable(name="WhiteList")
    private List<Pair> pairs;


    public Message(){}

    public Message(String publisherUsername, String content, Location location, List<Pair> pairs) {
        PublisherUsername = publisherUsername;
        Content = content;
  //      this.location = location;
        this.date = date.now();
        this.pairs = pairs;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getPublisherUsername() {
        return PublisherUsername;
    }

    public void setPublisherUsername(String publisherUsername) {
        PublisherUsername = publisherUsername;
    }

    public List<Pair> getPairs() {
        return pairs;
    }

    public void setPairs(List<Pair> pairs) {
        this.pairs = pairs;
    }
}
