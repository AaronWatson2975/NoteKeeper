package aaron.watson.notekeeper.android.job;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.net.Uri;
import android.os.AsyncTask;

import aaron.watson.notekeeper.note.NoteUploader;

public class NoteUploaderJobService extends JobService {
    public static final String EXTRA_DATA_URI = "aaron.watson.notekeeper.extras.DATA_URI";
    private NoteUploader mNoteUploader;

    public NoteUploaderJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        AsyncTask<JobParameters, Void, Void> task = new AsyncTask<JobParameters, Void, Void>() {
            @Override
            protected Void doInBackground(JobParameters... backgroundParams) {
                JobParameters jobParams = backgroundParams[0];
                Uri dataUri = Uri.parse(jobParams.getExtras().getString(EXTRA_DATA_URI));
                mNoteUploader.doUpload(dataUri);
                jobFinished(jobParams, false);
                return null;
            }
        };

        mNoteUploader = new NoteUploader(this);
        task.execute(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mNoteUploader.cancel();
        return true;
    }
}
