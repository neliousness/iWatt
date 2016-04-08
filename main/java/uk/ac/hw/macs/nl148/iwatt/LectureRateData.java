package uk.ac.hw.macs.nl148.iwatt;

/**
 * Created by mrnel on 06/04/2016.
 */
public class LectureRateData {

    private String question;
    float rating;

    LectureRateData() {}

    public LectureRateData(String question, float rating) {
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
        return "LectureRateData{" +
                "question='" + question + '\'' +
                ", rating=" + rating +
                '}';
    }
}
