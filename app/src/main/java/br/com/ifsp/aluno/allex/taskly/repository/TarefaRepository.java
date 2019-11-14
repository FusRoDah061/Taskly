package br.com.ifsp.aluno.allex.taskly.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public class TarefaRepository {

    public List<Tarefa> findAll() {
        List<Tarefa> tarefas = new ArrayList<>();

        tarefas.add(new Tarefa("Tarefa #1", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #2", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #3", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #4", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #5", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #6", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #7", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #8", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #9", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #10", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #11", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #12", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #13", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #14", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #15", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #16", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #17", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #18", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #19", new Date(), false));
        tarefas.add(new Tarefa("Tarefa #20", new Date(), false));

        return tarefas;
    }

}
