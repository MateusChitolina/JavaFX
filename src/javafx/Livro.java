package javafx;

public class Livro {
    private String title;
    private String author;
    private Long publishmentYear;
    private String publisher;
    private Long quantity;

    public Livro(String title, String author, Long publishmentYear, String publisher, Long quantity) {
        this.title = title;
        this.author = author;
        this.publishmentYear = publishmentYear;
        this.publisher = publisher;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getPublishmentYear() {
        return publishmentYear;
    }

    public void setPublishmentYear(Long publishmentYear) {
        this.publishmentYear = publishmentYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}

