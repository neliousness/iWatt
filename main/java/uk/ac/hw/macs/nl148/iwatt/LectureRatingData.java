package uk.ac.hw.macs.nl148.iwatt;

/**
 * Created by mrnel on 06/04/2016.
 */

/**
 * Author: Neio Lucas
 * File : LectureRatingData.java
 * Platform : Android Operating System
 * Date:  06/04/2016..
 * Description: This class is a pojo. it stores ratings and questions for the Lecture Rating feature
 */
public class LectureRatingData {

    private String question;
    float rating;

    LectureRatingData() {}

    public LectureRatingData(String question, float rating) {
        this.question = question;
        this.rating = rating;

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "LectureRatingData{" +
                "question='" + question + '\'' +
                ", rating=" + rating +
                '}';
    }
}
