package br.com.ifsp.aluno.allex.taskly.events;

import android.view.View;

import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public interface OnTarefaStatusChangedListener {
    void onTarefaStatusChanged(View view, int position, boolean isChecked, Tarefa tarefa);
}
