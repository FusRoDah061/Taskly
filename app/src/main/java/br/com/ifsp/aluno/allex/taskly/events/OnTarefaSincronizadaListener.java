package br.com.ifsp.aluno.allex.taskly.events;

import br.com.ifsp.aluno.allex.taskly.enums.ETarefaSincronizadaResult;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public interface OnTarefaSincronizadaListener {

    void onTarefaSincronizada(Tarefa tarefa, ETarefaSincronizadaResult result, Exception error);

}
