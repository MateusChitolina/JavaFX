package javafx;

public class Livro {
    private String title;
    private String author;
    private int publishmentYear;
    private String publisher;
    private int quantity;

    public Livro(String title, String author, int publishmentYear, String publisher, int quantity) {
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

    public int getPublishmentYear() {
        return publishmentYear;
    }

    public void setPublishmentYear(int publishmentYear) {
        this.publishmentYear = publishmentYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

