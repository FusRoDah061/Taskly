package br.com.ifsp.aluno.allex.taskly.events;

import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public interface OnTarefaActionListener {
    void onEditarTarefa(Tarefa tarefa);
    void onExcluirTarefa(Tarefa tarefa);
}
