package br.com.ifsp.aluno.allex.taskly.tasklyweb.api.tasks;

import android.os.AsyncTask;

import java.io.IOException;

import br.com.ifsp.aluno.allex.taskly.events.OnAsyncTaskFinishListener;
import br.com.ifsp.aluno.allex.taskly.views.AsyncActivity;

public abstract class TasklyAsyncTask<A, B> extends AsyncTask<A, Void, B> {

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
    protected final B doInBackground(A... params) {

        try {
            return doWork(params);
        } catch (Exception e) {
            error = e;
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected final void onPostExecute(B result) {
        super.onPostExecute(result);

        activity.asyncTaskFinished();

        if(onAsyncTaskFinishListener != null)
            onAsyncTaskFinishListener.onAsyncTaskFinished(result, this.error);
    }

    abstract protected B doWork(A... params) throws IOException;

    public void setOnAsyncTaskFinishListener(OnAsyncTaskFinishListener onAsyncTaskFinishListener) {
        this.onAsyncTaskFinishListener = onAsyncTaskFinishListener;
    }
}