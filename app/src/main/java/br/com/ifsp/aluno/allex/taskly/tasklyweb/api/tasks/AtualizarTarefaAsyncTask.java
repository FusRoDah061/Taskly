package br.com.ifsp.aluno.allex.taskly.tasklyweb.api.tasks;

import java.io.IOException;

import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.TarefaDTO;
import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.TasklyWebClient;
import br.com.ifsp.aluno.allex.taskly.views.AsyncActivity;

public class AtualizarTarefaAsyncTask extends TasklyAsyncTask<TarefaDTO, TarefaDTO> {
    public AtualizarTarefaAsyncTask(AsyncActivity activity) {
        super(activity);
    }

    @Override
    protected TarefaDTO doWork(TarefaDTO... params) throws IOException {
        TasklyWebClient tasklyWebClient = new TasklyWebClient();
        TarefaDTO tarefaDTO = params[0];

        try {
            return tasklyWebClient.atualizaTarefa(tarefaDTO);
        }
        catch (Exception e) {
            this.error = e;
            e.printStackTrace();
            return null;
        }
    }
}
