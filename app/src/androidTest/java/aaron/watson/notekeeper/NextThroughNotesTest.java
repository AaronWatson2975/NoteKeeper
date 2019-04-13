package aaron.watson.notekeeper;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import java.util.List;




public class NextThroughNotesTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void NextThroughNotes() {

    }
}