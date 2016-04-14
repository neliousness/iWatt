package uk.ac.hw.macs.nl148.iwatt;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by mrnel on 12/04/2016.
 */

@RunWith(AndroidJUnit4.class)
public class LectureRatingUITest {

    @Rule
    public final ActivityRule<LectureRating> main = new ActivityRule<>(LectureRating.class);


    @Test
      public void displayLectureRatingTitle()
    {
        //activity titile
        onView(withText("Lecture Rating")).check(ViewAssertions.matches(isDisplayed()));


    }

    @Test
    public void displayDropDownCourseList()
    {
        //activity titile
        onView(withText("Lecture Rating")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("[Select a course]")).check(ViewAssertions.matches(isDisplayed()));


    }


}
