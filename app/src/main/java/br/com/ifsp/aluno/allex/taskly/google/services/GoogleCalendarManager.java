package br.com.ifsp.aluno.allex.taskly.google.services;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.OperationCanceledException;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.enums.ETarefaSincronizadaResult;
import br.com.ifsp.aluno.allex.taskly.events.OnTarefaSincronizadaListener;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public class GoogleCalendarManager implements DialogInterface.OnCancelListener {

    private static GoogleCalendarManager instance = null;

    private Activity activity;
    private OnTarefaSincronizadaListener onTarefaSincronizadaListener;

    private Tarefa tarefa;

    private GoogleCalendarManager(Activity activity){
        this.activity = activity;
    }

    public static GoogleCalendarManager getInstance (Activity activity){
        if (instance == null)
            instance = new GoogleCalendarManager(activity);

        return instance;
    }

    public void sincronizarTarefa(Tarefa tarefa) {
        GoogleCommons googleCommons = new GoogleCommons();
        this.tarefa = tarefa;

        if(googleCommons.isGooglePlayServicesAvailable(activity)) {
            //TODO: Sincronizar tarefa
            // Ref: https://github.com/google/google-api-java-client-samples/blob/master/calendar-android-sample/src/main/java/com/google/api/services/samples/calendar/android/CalendarSampleActivity.java
        }
        else {
            googleCommons.showGooglePlayServicesAvailabilityErrorDialog(activity, googleCommons.getConnectionResult(), this);
        }
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        if(onTarefaSincronizadaListener != null)
            onTarefaSincronizadaListener.onTarefaSincronizada(tarefa, ETarefaSincronizadaResult.ERROR, new OperationCanceledException(activity.getResources().getString(R.string.error_gplay_services_unavailable)));
    }

    public void setOnTarefaSincronizadaListener(OnTarefaSincronizadaListener onTarefaSincronizadaListener) {
        this.onTarefaSincronizadaListener = onTarefaSincronizadaListener;
    }
}
