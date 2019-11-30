package br.com.ifsp.aluno.allex.taskly.persistence.repository;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.enums.EStatusTarefa;
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

    public List<Tarefa> findForDays(int qtdDias, boolean ignoraHoje) {

        Calendar c = Calendar.getInstance();
        Date inicio;
        Date limite;

        c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        if(ignoraHoje)
            c.add(Calendar.DAY_OF_MONTH, 1);

        inicio = c.getTime();

        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.add(Calendar.DAY_OF_MONTH, qtdDias);
        limite = c.getTime();

        String filter = String.format(Locale.getDefault(), "%s >= '%s' and %s <= '%s'", Constantes.COLUMN_TAREFA_DATA_LIMITE, Constantes.SQLITE_DATE_TIME_FORMAT.format(inicio), Constantes.COLUMN_TAREFA_DATA_LIMITE, Constantes.SQLITE_DATE_TIME_FORMAT.format(limite));

        return dao.get(filter);
    }

    public Tarefa findById(Long tarefaId) {
        String filter = String.format(Locale.getDefault(), "%s = %d ", Constantes.COLUMN_TAREFA_ID, tarefaId);

        try {
            return dao.get(filter).get(0);
        }
        catch (IndexOutOfBoundsException e) {
            return null;
        }

    }

    public Tarefa findByTasklyId(Long tarefaId) {
        String filter = String.format(Locale.getDefault(), "%s = %d", Constantes.COLUMN_TAREFA_TASKLY_ID, tarefaId);

        try {
            return dao.get(filter).get(0);
        }
        catch (IndexOutOfBoundsException e) {
            return null;
        }

    }

    public List<Tarefa> findAllFromDate(Date ref) {
        String filter = String.format(Locale.getDefault(), "%s >= '%s'", Constantes.COLUMN_TAREFA_DATA_LIMITE, Constantes.SQLITE_DATE_TIME_FORMAT.format(ref));

        return dao.get(filter);
    }

    public List<Tarefa> findAllBeforeDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        String filter = String.format(Locale.getDefault(), "%s < '%s'", Constantes.COLUMN_TAREFA_DATA_LIMITE, Constantes.SQLITE_DATE_TIME_FORMAT.format(c.getTime()));

        return dao.get(filter);
    }

    public List<Tarefa> findByStatus(EStatusTarefa status) {
        String filter = String.format(Locale.getDefault(), "%s = '%s'", Constantes.COLUMN_TAREFA_STATUS, status.getStatus());

        return dao.get(filter);
    }

    public List<Tarefa> findAllSynced() {
        return dao.get(String.format(Locale.getDefault(), "%s = 1", Constantes.COLUMN_TAREFA_SINCRONIZADA));
    }
}
