package br.com.ifsp.aluno.allex.taskly.viewmodels;

import android.content.Context;

import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public abstract class BaseViewModel extends SequenceViewModel {

    protected Tarefa tarefa;
    protected Context context;

    BaseViewModel(Tarefa tarefa, Context context) {
        this.tarefa = tarefa;
        this.context = context;
    }
}
