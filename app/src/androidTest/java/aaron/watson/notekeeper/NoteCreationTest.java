package aaron.watson.notekeeper;

import android.provider.ContactsContract;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static org.hamcrest.Matchers.*;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.*;

@RunWith(AndroidJUnit4.class)
public class NoteCreationTest {

    static DataManager sDataManager;

    @BeforeClass
    public static void classSetUp() throws Exception {
        sDataManager = DataManager.getInstance();
    }

    @Rule
    public ActivityTestRule<NoteListActivity> mNoteListActivityRule =
            new ActivityTestRule<>(NoteListActivity.class);

    @Test
    public void createNewNote() {

        final CourseInfo course = sDataManager.getCourse("java lang");
        final String noteTitle = "Test note title";
        final String noteText = "This is the body of our test note";

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.spinner_courses)).perform(click());
        onData(allOf(instanceOf(CourseInfo.class), equalTo(course))).perform(click());
        onView(withId(R.id.spinner_courses)).check(matches(withSpinnerText(
                containsString(course.getTitle()))));

        onView(withId(R.id.text_note_title)).perform(typeText("Test note title"))
                .check(matches(withText(containsString(noteTitle))));

        onView(withId(R.id.text_note_text)).perform(typeText("This is the body of our test bote"),
                ViewActions.closeSoftKeyboard());

        onView(withId(R.id.text_note_text)).check(matches(withText(containsString(noteText))));
        onView(withId(R.id.text_note_title)).check(matches(withText(containsString(noteTitle))));

        pressBack();

        final int noteIndex = sDataManager.getNotes().size() - 1;
        final NoteInfo note = sDataManager.getNotes().get(noteIndex);
        
        assertEquals(note.getCourse(), course);
        assertEquals(noteTitle, note.getTitle());
        assertEquals(noteText, note.getText());
    }
}