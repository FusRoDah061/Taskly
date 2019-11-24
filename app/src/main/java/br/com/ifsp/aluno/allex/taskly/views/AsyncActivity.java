package br.com.ifsp.aluno.allex.taskly.views;

import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

public abstract class AsyncActivity extends AppCompatActivity implements DialogInterface.OnCancelListener {

    protected int numAsyncTasks = 0;

    public int getNumAsyncTasks() {
        return numAsyncTasks;
    }

    public void newAsyncTasks() {
        this.numAsyncTasks++;
    }

    public void asyncTaskFinished() {
        this.numAsyncTasks--;
        if(this.numAsyncTasks < 0) this.numAsyncTasks = 0;
    }

    protected abstract void onGooglePlayServicesCancel(DialogInterface dialog);

    @Override
    public void onCancel(DialogInterface dialog) {
        onGooglePlayServicesCancel(dialog);
    }
}
