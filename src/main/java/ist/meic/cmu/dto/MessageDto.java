package ist.meic.cmu.dto;

import java.util.Date;

/**
 * Created by Diogo on 28/04/2017.
 */
public class MessageDto {

    private String content;
    private String publisher;
    private Date publicationDate;

    public MessageDto(String content, String publisher, Date publicationDate) {
        this.content = content;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
}
