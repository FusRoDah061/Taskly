package br.com.ifsp.aluno.allex.taskly.viewmodels;

import android.app.Activity;

import androidx.databinding.Bindable;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.google.services.GoogleCalendarManager;
import br.com.ifsp.aluno.allex.taskly.enums.ETarefaSincronizadaResult;
import br.com.ifsp.aluno.allex.taskly.events.OnTarefaSincronizadaListener;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.persistence.repository.TarefaRepository;

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
            return "Ah, e sua tarefa será sincronizada com sua agenda Google!";
        else
            return "";
    }

    public void onLegalClicked() {
        new TarefaRepository(activity).save(tarefa);

        if(tarefa.isSincronizada()){
            GoogleCalendarManager googleCalendarManager = GoogleCalendarManager.getInstance(activity);
            googleCalendarManager.setOnTarefaSincronizadaListener(this);

            googleCalendarManager.sincronizarTarefa(tarefa);
        }
        else {
            finalizarCriacaoTarefa();
        }
    }

    private void finalizarCriacaoTarefa() {
        //TODO: Registrar notificação

        goToNextFragment();
    }

    @Override
    public void onTarefaSincronizada(Tarefa tarefa, ETarefaSincronizadaResult result, Exception error) {
        if(result == ETarefaSincronizadaResult.SUCCESS) {
            finalizarCriacaoTarefa();
        }
        else{
            error.printStackTrace();
        }
    }
}
