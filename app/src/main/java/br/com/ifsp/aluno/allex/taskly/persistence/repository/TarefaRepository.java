package br.com.ifsp.aluno.allex.taskly.persistence.repository;

import android.content.Context;

import java.util.List;

import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.persistence.dao.TarefaDAO;

public class TarefaRepository {

    private TarefaDAO dao;

    public TarefaRepository(Context context) {
        dao = new TarefaDAO(context);
    }

    public List<Tarefa> findAll() {
        return dao.get(null);
    }

    public void save(Tarefa tarefa) {

        if(tarefa.getId() == null)
            dao.save(tarefa);
        else
            dao.update(tarefa);
    }

    public boolean delete(Tarefa tarefa) {
        return dao.delete(tarefa);
    }
}
