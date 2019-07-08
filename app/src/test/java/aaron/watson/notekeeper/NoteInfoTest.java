package aaron.watson.notekeeper;

import android.os.Parcel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import aaron.watson.notekeeper.course.CourseInfo;
import aaron.watson.notekeeper.data.DatabaseManager;
import aaron.watson.notekeeper.note.NoteInfo;

/**
 * Created by Aaron on 2019-04-09.
 */

public class NoteInfoTest {

    private static DatabaseManager sDatabaseManager;

    @BeforeClass
    public static void classSetUp() throws Exception {
        sDatabaseManager = DatabaseManager.getInstance();
    }

    @Before
    public void setUp() throws Exception {
        sDatabaseManager.getNotes().clear();
        sDatabaseManager.initializeExampleNotes();
    }


    @Test
    public void NoteInfoTest() {

        CourseInfo course = sDatabaseManager.getCourse("android_async");
        String title = "Test Title";
        String body = "This is the body of text for the test note.";

        NoteInfo note = new NoteInfo(course, title, body);
        NoteInfo noteCopy = new NoteInfo(course, title, body);

        String expectedCompareKey = course.getCourseId() + "|" + title + "|" + body;
        String compareKey = note.toString();

        int hash = note.hashCode();
        int expectedHash = -406337001;

        Parcel parcel = MockParcel.obtain();
        note.writeToParcel(parcel, note.describeContents());
        parcel.setDataPosition(0);
        NoteInfo createdFromParcel = NoteInfo.CREATOR.createFromParcel(parcel);

        // TODO:  The MockParcel object can't handle nested parcels like the course in the note.
        Assert.assertEquals(createdFromParcel.getTitle(), note.getTitle());
        Assert.assertEquals(createdFromParcel.getText(), note.getText());
        Assert.assertTrue(note.equals(noteCopy));
        Assert.assertEquals(expectedCompareKey, compareKey);
        Assert.assertEquals(expectedHash, hash);
    }

    @Test
    public void noteInfoIdTest() {
        CourseInfo course = sDatabaseManager.getCourse("android_async");
        String title = "Test Title";
        String body = "This is the body of text for the test note.";
        int id = 777;

        NoteInfo note = new NoteInfo(id, course, title, body);
        Assert.assertEquals(note.getId(), id);
    }
}
