package br.com.ifsp.aluno.allex.taskly.viewmodels;

import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.views.AsyncActivity;

public abstract class BaseViewModel extends SequenceViewModel {

    protected Tarefa tarefa;
    protected AsyncActivity activity;

    BaseViewModel(Tarefa tarefa, AsyncActivity activity) {
        this.tarefa = tarefa;
        this.activity = activity;
    }
}
