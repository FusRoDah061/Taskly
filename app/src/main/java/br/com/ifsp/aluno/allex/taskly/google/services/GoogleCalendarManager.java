package br.com.ifsp.aluno.allex.taskly.google.services;

import android.content.DialogInterface;
import android.os.OperationCanceledException;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;

import javax.annotation.Nullable;

import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.enums.ETarefaSincronizadaResult;
import br.com.ifsp.aluno.allex.taskly.events.OnAsyncTaskFinishListener;
import br.com.ifsp.aluno.allex.taskly.events.OnTarefaSincronizadaListener;
import br.com.ifsp.aluno.allex.taskly.google.services.tasks.AsyncInsertTarefa;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.views.AsyncActivity;

public class GoogleCalendarManager implements DialogInterface.OnCancelListener, OnAsyncTaskFinishListener {

    private static GoogleCalendarManager instance = null;

    private AsyncActivity activity;
    private OnTarefaSincronizadaListener onTarefaSincronizadaListener;

    private final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    private final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

    private Tarefa tarefa;

    private GoogleCalendarManager(AsyncActivity activity){
        this.activity = activity;
    }

    public static GoogleCalendarManager getInstance (AsyncActivity activity){
        if (instance == null)
            instance = new GoogleCalendarManager(activity);

        return instance;
    }

    public void criarTarefa(Tarefa tarefa, String account) {

        if(account == null)
            throw  new NullPointerException(activity.getResources().getString(R.string.error_account_null));

        GoogleCommons googleCommons = new GoogleCommons();

        this.tarefa = tarefa;

        if(googleCommons.isGooglePlayServicesAvailable(activity)) {
            GoogleAccountManager googleAccountManager = new GoogleAccountManager(activity);
            googleAccountManager.setAccountName(account);

            Calendar calendarClient = new Calendar.Builder(
                    transport, jsonFactory, googleAccountManager.getCredential()).setApplicationName(activity.getResources().getString(R.string.app_name))
                    .build();

            AsyncInsertTarefa asyncInsertTarefa = new AsyncInsertTarefa(activity, calendarClient, tarefa);
            asyncInsertTarefa.setOnAsyncTaskFinishListener(this);
            asyncInsertTarefa.execute();
        }
        else {
            googleCommons.showGooglePlayServicesAvailabilityErrorDialog(activity, googleCommons.getConnectionResult(), this);
        }
    }

    public void removerTarefa(Tarefa tarefa, String account) {
        //TODO: Remover tarefa do google
    }

    public void atualizarTarefa(Tarefa tarefa, String account) {
        //TODO: atualizar tarefa no google
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        if(onTarefaSincronizadaListener != null)
            onTarefaSincronizadaListener.onTarefaSincronizada(
                    tarefa, ETarefaSincronizadaResult.ERROR, new OperationCanceledException(activity.getResources().getString(R.string.error_gplay_services_unavailable)));
    }

    public void setOnTarefaSincronizadaListener(OnTarefaSincronizadaListener onTarefaSincronizadaListener) {
        this.onTarefaSincronizadaListener = onTarefaSincronizadaListener;
    }

    @Override
    public void onAsyncTaskFinished(Boolean success, @Nullable Exception error) {
        if(onTarefaSincronizadaListener != null)
            if(success)
                onTarefaSincronizadaListener.onTarefaSincronizada(tarefa, ETarefaSincronizadaResult.SUCCESS, null);
            else
                onTarefaSincronizadaListener.onTarefaSincronizada(tarefa, ETarefaSincronizadaResult.ERROR, error);
    }
}
