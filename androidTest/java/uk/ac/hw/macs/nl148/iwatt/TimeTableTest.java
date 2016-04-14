package uk.ac.hw.macs.nl148.iwatt;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by mrnel on 12/04/2016.
 */

@RunWith(AndroidJUnit4.class)
public class TimeTableTest {

    @Rule
    public final ActivityRule<TimeTable> main = new ActivityRule<>(TimeTable.class);


    @Test
      public void displayTimeTableTitle()
    {
        //activity titile
        onView(withText("TimeTable")).check(ViewAssertions.matches(isDisplayed()));

    }



}
