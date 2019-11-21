package br.com.ifsp.aluno.allex.taskly.viewmodels;

import android.app.Activity;
import android.content.Context;

import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public abstract class BaseViewModel extends SequenceViewModel {

    protected Tarefa tarefa;
    protected Activity activity;

    BaseViewModel(Tarefa tarefa, Activity activity) {
        this.tarefa = tarefa;
        this.activity = activity;
    }
}
