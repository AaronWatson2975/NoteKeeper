package aaron.watson.notekeeper.android.service;

import android.app.IntentService;
import android.content.Intent;

import aaron.watson.notekeeper.note.NoteBackup;

public class NoteBackupService extends IntentService {
    public static final String EXTRA_COURSE_ID = "aaron.watson.notekeeper.note.extra.COURSE_ID";

    public NoteBackupService() {
        super("NoteBackupService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String backupCourseId = intent.getStringExtra(EXTRA_COURSE_ID);
            NoteBackup.doBackup(this, backupCourseId);
        }
    }
}
