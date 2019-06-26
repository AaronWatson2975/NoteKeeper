package aaron.watson.notekeeper;

import android.os.Parcel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import aaron.watson.notekeeper.course.CourseInfo;
import aaron.watson.notekeeper.data.DataManager;
import aaron.watson.notekeeper.note.NoteInfo;

/**
 * Created by Aaron on 2019-04-09.
 */

public class NoteInfoTest {

    static DataManager sDataManager;

    @BeforeClass
    public static void classSetUp() throws Exception {
        sDataManager = DataManager.getInstance();
    }

    @Before
    public void setUp() throws Exception {
        sDataManager.getNotes().clear();
        sDataManager.initializeExampleNotes();
    }


    @Test
    public void NoteInfoTest() {

        CourseInfo course = sDataManager.getCourse("android_async");
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
}
