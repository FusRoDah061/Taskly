package br.com.ifsp.aluno.allex.taskly.viewmodels;

import android.widget.Toast;

import androidx.databinding.Bindable;

import javax.annotation.Nullable;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.events.OnAsyncTaskFinishListener;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.notifications.TarefaNotificationReceiver;
import br.com.ifsp.aluno.allex.taskly.persistence.repository.TarefaRepository;
import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.TarefaDTO;
import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.TasklyWebClient;
import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.tasks.CriarTarefaAsyncTask;
import br.com.ifsp.aluno.allex.taskly.views.AsyncActivity;

public class ConcluirViewModel extends BaseViewModel {

    public ConcluirViewModel(Tarefa tarefa, AsyncActivity activity) {
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
            TarefaDTO tarefaDTO = TasklyWebClient.mapTarefaToTarefaDTO(tarefa);
            CriarTarefaAsyncTask criarTarefaAsyncTask = new CriarTarefaAsyncTask(activity);

            criarTarefaAsyncTask.setOnAsyncTaskFinishListener(new OnAsyncTaskFinishListener<Boolean>() {
                @Override
                public void onAsyncTaskFinished(Boolean result, @Nullable Exception error) {
                    if (result) {
                        finalizarCriacaoTarefa();
                    }
                    else {
                        if(error != null) {
                            error.printStackTrace();
                        }

                        Toast.makeText(activity, "Erro ao enviar a tarefa.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            criarTarefaAsyncTask.execute(tarefaDTO);
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
}
