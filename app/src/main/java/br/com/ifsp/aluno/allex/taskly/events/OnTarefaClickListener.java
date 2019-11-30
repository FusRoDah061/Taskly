package br.com.ifsp.aluno.allex.taskly.events;

import android.view.View;

import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public interface OnTarefaClickListener {
    void onTarefaClicked(View view, int position, Tarefa tarefa);
}
