package aaron.watson.notekeeper.note;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import aaron.watson.notekeeper.NoteKeeperProviderContract.Notes;

public class NoteUploader {
    private final String TAG = getClass().getSimpleName();

    private final Context mContext;
    private boolean mCanceled;

    public NoteUploader(Context context) {
        mContext = context;
    }

    public boolean isCanceled() {
        return mCanceled;
    }

    public void cancel() {
        mCanceled = true;
    }

    public void doUpload(Uri dataUri) {
        String[] columns = {
                Notes.COLUMN_COURSE_ID,
                Notes.COLUMN_NOTE_TITLE,
                Notes.COLUMN_NOTE_TEXT,
        };

        Cursor cursor = mContext.getContentResolver().query(dataUri, columns, null, null, null);
        int courseIdPos = cursor.getColumnIndex(Notes.COLUMN_COURSE_ID);
        int noteTitlePos = cursor.getColumnIndex(Notes.COLUMN_NOTE_TITLE);
        int noteTextPos = cursor.getColumnIndex(Notes.COLUMN_NOTE_TEXT);

        Log.i(TAG, ">>>*** UPLOAD START - " + dataUri + "***<<<");
        mCanceled = false;
        while(!mCanceled && cursor.moveToNext()) {
            String courseId = cursor.getString(courseIdPos);
            String noteTitle = cursor.getString(noteTitlePos);
            String noteText = cursor.getString(noteTextPos);

            if(!noteTitle.equals("")) {
                Log.i(TAG, ">>>   UPLOADING NOTE   <<<  -> " + courseId + "|" + noteTitle + "|" + noteText );
                simulateLongRunningWork();
            }
        }
        if(!mCanceled) {
            Log.i(TAG, ">>>***   UPLOAD COMPLETE   ***<<<");
        }
    }

    private void simulateLongRunningWork() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
