package br.com.ifsp.aluno.allex.taskly.tasklyweb.api.tasks;

import java.io.IOException;
import java.util.List;

import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.TarefaDTO;
import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.TasklyWebClient;
import br.com.ifsp.aluno.allex.taskly.views.AsyncActivity;

public class ListarTarefasAsyncTask extends TasklyAsyncTask<String, List<TarefaDTO>> {

    public ListarTarefasAsyncTask(AsyncActivity activity) {
        super(activity);
    }

    @Override
    protected List<TarefaDTO> doWork(String... params) throws IOException {
        TasklyWebClient client = new TasklyWebClient();
        String account = params[0];

        return client.getTarefas(account);
    }

}
