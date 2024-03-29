package br.com.ifsp.aluno.allex.taskly.tasklyweb.api.tasks;

import java.io.IOException;

import br.com.ifsp.aluno.allex.taskly.persistence.dao.TarefaDAO;
import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.TarefaDTO;
import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.TasklyWebClient;
import br.com.ifsp.aluno.allex.taskly.views.AsyncActivity;

public class DeletarTarefaAsyncTask extends TasklyAsyncTask<TarefaDTO, Boolean> {
    public DeletarTarefaAsyncTask(AsyncActivity activity) {
        super(activity);
    }

    @Override
    protected Boolean doWork(TarefaDTO... params) throws IOException {
        TasklyWebClient tasklyWebClient = new TasklyWebClient();
        TarefaDTO tarefaDTO = params[0];

        try {
            tasklyWebClient.deleteTarefa(tarefaDTO.getId());
            return true;
        }
        catch (Exception e) {
            this.error = e;
            e.printStackTrace();
        }

        return false;
    }
}
