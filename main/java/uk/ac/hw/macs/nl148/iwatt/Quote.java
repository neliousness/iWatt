package uk.ac.hw.macs.nl148.iwatt;

/**
 * Created by mrnel on 14/04/2016.
 */
public class Quote {

    private String author;
    private String saying;


    public Quote() {
    }

    public Quote(String author, String saying) {
        this.author = author;
        this.saying = saying;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSaying() {
        return saying;
    }

    public void setSaying(String saying) {
        this.saying = saying;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "author='" + author + '\'' +
                ", saying='" + saying + '\'' +
                '}';
    }
}
