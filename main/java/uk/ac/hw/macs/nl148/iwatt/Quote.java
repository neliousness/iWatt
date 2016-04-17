package uk.ac.hw.macs.nl148.iwatt;

/**
 * Author: Neio Lucas
 * File : Quote.java
 * Platform : Android Operating System
 * Date:  04/02/2016.
 * Description: This class is a pojo. it stores data from an online database
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
