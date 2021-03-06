package ist.meic.cmu.dto;

/**
 * Created by Diogo on 28/04/2017.
 */
public class MessageDto {

    private Integer id;
    private String title;
    private String content;
    private String publisher;
    private String publicationDate;

    public MessageDto(Integer id, String title, String content, String publisher, String publicationDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
    }

    public MessageDto(){

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

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageDto that = (MessageDto) o;

        return this.id.equals(that.id);
    }

}
