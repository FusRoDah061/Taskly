package br.com.ifsp.aluno.allex.taskly.views;

import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

public abstract class AsyncActivity extends AppCompatActivity {

    protected int numAsyncTasks = 0;

    public int getNumAsyncTasks() {
        return numAsyncTasks;
    }

    public void asyncTaskStarted() {
        this.numAsyncTasks++;
    }

    public void asyncTaskFinished() {
        this.numAsyncTasks--;
        if(this.numAsyncTasks < 0) this.numAsyncTasks = 0;
    }
}
