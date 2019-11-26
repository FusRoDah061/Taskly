package br.com.ifsp.aluno.allex.taskly.tasklyweb.api.tasks;

import android.os.AsyncTask;

import java.io.IOException;

import br.com.ifsp.aluno.allex.taskly.events.OnAsyncTaskFinishListener;
import br.com.ifsp.aluno.allex.taskly.views.AsyncActivity;

public abstract class TasklyAsyncTask extends AsyncTask<Void, Void, Boolean> {

    protected final AsyncActivity activity;

    private OnAsyncTaskFinishListener onAsyncTaskFinishListener;
    private Exception error;

    public TasklyAsyncTask(AsyncActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.asyncTaskStarted();
    }

    @Override
    protected final Boolean doInBackground(Void... ignored) {

        try {
            doInBackground();
            return true;
        } catch (Exception e) {
            error = e;
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected final void onPostExecute(Boolean success) {
        super.onPostExecute(success);

        activity.asyncTaskFinished();

        if(onAsyncTaskFinishListener != null)
            onAsyncTaskFinishListener.onAsyncTaskFinished(success, this.error);
    }

    abstract protected void doInBackground() throws IOException;

    public void setOnAsyncTaskFinishListener(OnAsyncTaskFinishListener onAsyncTaskFinishListener) {
        this.onAsyncTaskFinishListener = onAsyncTaskFinishListener;
    }
}