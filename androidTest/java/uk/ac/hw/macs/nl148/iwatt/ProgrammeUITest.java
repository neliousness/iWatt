package uk.ac.hw.macs.nl148.iwatt;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by mrnel on 12/04/2016.
 */

@RunWith(AndroidJUnit4.class)
public class ProgrammeUITest {

    @Rule
    public final ActivityRule<Programme> main = new ActivityRule<>(Programme.class);



    @Test
    public void displayProgrammeTitle()
    {

        onView(withText("Programme")).check(ViewAssertions.matches(isDisplayed()));

    }

}
