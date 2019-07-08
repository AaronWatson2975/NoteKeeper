package aaron.watson.notekeeper;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import aaron.watson.notekeeper.course.CourseInfo;
import aaron.watson.notekeeper.data.DatabaseManager;
import aaron.watson.notekeeper.note.NoteInfo;

import static org.junit.Assert.*;

public class DatabaseManagerTest {
    static DatabaseManager sDatabaseManager;

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
    public void createNewNote() throws Exception {
        final CourseInfo course = sDatabaseManager.getCourse("android_async");
        final String noteTitle = "Test note title";
        final String noteText = "This is the body text of my test note";

        int noteIndex = sDatabaseManager.createNewNote();
        NoteInfo newNote = sDatabaseManager.getNotes().get(noteIndex);
        newNote.setCourse(course);
        newNote.setTitle(noteTitle);
        newNote.setText(noteText);

        NoteInfo compareNote = sDatabaseManager.getNotes().get(noteIndex);
        assertEquals(course, compareNote.getCourse());
        assertEquals(noteTitle, compareNote.getTitle());
        assertEquals(noteText, compareNote.getText());
    }

    @Test
    public void findSimilarNotes() throws Exception {
        final CourseInfo course = sDatabaseManager.getCourse("android_async");
        final String noteTitle = "Test note title";
        final String noteText1 = "This is the body text of my test note";
        final String noteText2  = "This is the body of my second test note";

        int noteIndex1 = sDatabaseManager.createNewNote();
        NoteInfo newNote1 = sDatabaseManager.getNotes().get(noteIndex1);
        newNote1.setCourse(course);
        newNote1.setTitle(noteTitle);
        newNote1.setText(noteText1);

        int noteIndex2 = sDatabaseManager.createNewNote();
        NoteInfo newNote2 = sDatabaseManager.getNotes().get(noteIndex2);
        newNote2.setCourse(course);
        newNote2.setTitle(noteTitle);
        newNote2.setText(noteText2);

        int foundIndex1 = sDatabaseManager.findNote(newNote1);
        assertEquals(noteIndex1, foundIndex1);

        int foundIndex2 = sDatabaseManager.findNote(newNote2);
        assertEquals(noteIndex2, foundIndex2);

        int numberOfNotes = sDatabaseManager.getNoteCount(course);
        assertEquals(numberOfNotes, 4);

        List<NoteInfo> notes = sDatabaseManager.getNotes(course);

        int totalNotes = sDatabaseManager.getNotes().size();
        int firstNoteHash = sDatabaseManager.getNotes().get(0).hashCode();
        sDatabaseManager.removeNote(0);

        assertEquals(totalNotes-1, sDatabaseManager.getNotes().size());
        assertNotEquals(firstNoteHash, sDatabaseManager.getNotes().get(0).hashCode());
    }

    @Test
    public void createNewNoteOneStepCreation() {
        final CourseInfo course = sDatabaseManager.getCourse("android_async");
        final String noteTitle = "Test note title";
        final String noteText = "This is the body of my test note";

        int noteIndex = sDatabaseManager.createNewNote(course, noteTitle, noteText);

        NoteInfo compareNote = sDatabaseManager.getNotes().get(noteIndex);
        assertEquals(course, compareNote.getCourse());
        assertEquals(noteTitle, compareNote.getTitle());
        assertEquals(noteText, compareNote.getText());
    }

}