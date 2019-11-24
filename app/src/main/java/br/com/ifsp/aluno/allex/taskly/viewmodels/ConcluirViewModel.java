package br.com.ifsp.aluno.allex.taskly.viewmodels;

import android.app.Activity;

import androidx.databinding.Bindable;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.enums.ETarefaSincronizadaResult;
import br.com.ifsp.aluno.allex.taskly.events.OnTarefaSincronizadaListener;
import br.com.ifsp.aluno.allex.taskly.google.services.GoogleCalendarManager;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.notifications.TarefaNotificationReceiver;
import br.com.ifsp.aluno.allex.taskly.persistence.repository.TarefaRepository;
import br.com.ifsp.aluno.allex.taskly.views.AsyncActivity;

public class ConcluirViewModel extends BaseViewModel implements OnTarefaSincronizadaListener {

    public ConcluirViewModel(Tarefa tarefa, Activity activity) {
        super(tarefa, activity);
    }

    @Bindable
    public String getDescricaoTarefa() {
        return tarefa.getDescricao();
    }

    @Bindable
    public String getHoraTarefa() {
        return Constantes.TIME_FORMAT.format(tarefa.getData());
    }

    @Bindable
    public String getDataTarefa() {
        return Constantes.DATE_FORMAT.format(tarefa.getData());
    }

    @Bindable
    public String getIsSincronizada() {
        if(tarefa.isSincronizada())
            return activity.getResources().getString(R.string.str_tarefa_sincronizada_aviso);
        else
            return "";
    }

    public void onLegalClicked() {
        if(tarefa.isSincronizada()){
            GoogleCalendarManager googleCalendarManager = GoogleCalendarManager.getInstance((AsyncActivity) activity);
            googleCalendarManager.setOnTarefaSincronizadaListener(this);

            googleCalendarManager.criarTarefa(tarefa, tarefa.getGoogleAccount());
        }
        else {
            finalizarCriacaoTarefa();
        }
    }

    private void finalizarCriacaoTarefa() {
        new TarefaRepository(activity).save(tarefa);

        TarefaNotificationReceiver tarefaNotificationReceiver = new TarefaNotificationReceiver();
        tarefaNotificationReceiver.scheduleNotification(activity, tarefa);

        goToNextFragment();
    }

    @Override
    public void onTarefaSincronizada(Tarefa tarefa, ETarefaSincronizadaResult result, Exception error) {
        if(result == ETarefaSincronizadaResult.SUCCESS) {
            finalizarCriacaoTarefa();
        }
        else{
            error.printStackTrace();
            //TODO: Perguntar se quer tentar denovo
        }
    }
}
