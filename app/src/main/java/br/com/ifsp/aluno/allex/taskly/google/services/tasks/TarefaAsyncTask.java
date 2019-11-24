package br.com.ifsp.aluno.allex.taskly.google.services.tasks;

import com.google.api.services.calendar.Calendar;

import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.views.AsyncActivity;

public abstract class TarefaAsyncTask extends CalendarAsyncTask {

    protected Tarefa tarefa;

    public TarefaAsyncTask(AsyncActivity activity, Calendar client, Tarefa tarefa) {
        super(activity, client);
        this.tarefa = tarefa;
    }
}
