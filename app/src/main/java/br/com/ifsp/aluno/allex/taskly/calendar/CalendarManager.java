package br.com.ifsp.aluno.allex.taskly.calendar;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.OperationCanceledException;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.enums.ETarefaSincronizadaResult;
import br.com.ifsp.aluno.allex.taskly.events.OnTarefaSincronizadaListener;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public class CalendarManager implements DialogInterface.OnCancelListener {

    private static CalendarManager instance = null;

    private Activity activity;
    private OnTarefaSincronizadaListener onTarefaSincronizadaListener;

    private Tarefa tarefa;

    private CalendarManager(Activity activity){
        this.activity = activity;
    }

    public static CalendarManager getInstance (Activity activity){
        if (instance == null)
            instance = new CalendarManager(activity);

        return instance;
    }

    public void sincronizarTarefa(Tarefa tarefa) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        this.tarefa = tarefa;

        int result = googleApiAvailability.isGooglePlayServicesAvailable(activity);

        if(result == ConnectionResult.SUCCESS) {
            //TODO: Sincronizar tarefa
            // Ref: https://github.com/google/google-api-java-client-samples/blob/master/calendar-android-sample/src/main/java/com/google/api/services/samples/calendar/android/CalendarSampleActivity.java
        }
        else if(result == ConnectionResult.SERVICE_MISSING ||
                result == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED ||
                result == ConnectionResult.SERVICE_DISABLED) {
            Dialog dialog = googleApiAvailability.getErrorDialog(activity, result, Constantes.REQ_CODE_GOOGLE_API_UNAVAILABLE, this);
            dialog.show();
        }
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        if(onTarefaSincronizadaListener != null)
            onTarefaSincronizadaListener.onTarefaSincronizada(tarefa, ETarefaSincronizadaResult.ERROR, new OperationCanceledException("Google Play Services indisponível. Atualização cancelada pelo usuário."));
    }

    public void setOnTarefaSincronizadaListener(OnTarefaSincronizadaListener onTarefaSincronizadaListener) {
        this.onTarefaSincronizadaListener = onTarefaSincronizadaListener;
    }
}
