package br.com.ifsp.aluno.allex.taskly.persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.enums.EStatusTarefa;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.persistence.helper.TarefaSQLiteHelper;

public class TarefaDAO {

    private SQLiteDatabase db = null;
    private TarefaSQLiteHelper helper = null;

    public TarefaDAO(Context context) {
        helper = new TarefaSQLiteHelper(context);
    }

    public List<Tarefa> get(String where) {
        List<Tarefa> tarefas = new ArrayList<>();

        db = helper.getReadableDatabase();

        String sql = String.format("select * from %s %s", Constantes.TABLE_TAREFA, (where != null ? "where " + where : ""));

        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {

                Tarefa tarefa = new Tarefa();
                tarefa.setId(cursor.getLong(cursor.getColumnIndex(Constantes.COLUMN_TAREFA_ID)));
                tarefa.setDescricao(cursor.getString(cursor.getColumnIndex(Constantes.COLUMN_TAREFA_DESCRICAO)));
                tarefa.setSincronizada(cursor.getInt(cursor.getColumnIndex(Constantes.COLUMN_TAREFA_SINCRONIZADA)) == 1);
                tarefa.setStatus(EStatusTarefa.valueOf(cursor.getString(cursor.getColumnIndex(Constantes.COLUMN_TAREFA_STATUS))));
                tarefa.setLatitudeCriacao(cursor.getDouble(cursor.getColumnIndex(Constantes.COLUMN_TAREFA_LATITUDE_CRIACAO)));
                tarefa.setLongitudeCriacao(cursor.getDouble(cursor.getColumnIndex(Constantes.COLUMN_TAREFA_LONGITUDE_CRIACAO)));

                if(tarefa.isSincronizada()) {
                    tarefa.setTasklyTaskId(cursor.getLong(cursor.getColumnIndex(Constantes.COLUMN_TAREFA_TASKLY_ID)));
                    tarefa.setTasklyAccount(cursor.getString(cursor.getColumnIndex(Constantes.COLUMN_TAREFA_TASKLY_ACCOUNT)));
                }

                try {
                    tarefa.setDataLimite(Constantes.SQLITE_DATE_TIME_FORMAT.parse(cursor.getString(cursor.getColumnIndex(Constantes.COLUMN_TAREFA_DATA_LIMITE))));
                    tarefa.setDataCriacao(Constantes.SQLITE_DATE_TIME_FORMAT.parse(cursor.getString(cursor.getColumnIndex(Constantes.COLUMN_TAREFA_DATA_CRIACAO))));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                tarefas.add(tarefa);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tarefas;
    }

    public void save(Tarefa tarefa) throws SQLException {

        db = helper.getWritableDatabase();
        db.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(Constantes.COLUMN_TAREFA_DESCRICAO, tarefa.getDescricao());
        values.put(Constantes.COLUMN_TAREFA_DATA_LIMITE, Constantes.SQLITE_DATE_TIME_FORMAT.format(tarefa.getDataLimite()));
        values.put(Constantes.COLUMN_TAREFA_DATA_CRIACAO, Constantes.SQLITE_DATE_TIME_FORMAT.format(tarefa.getDataCriacao()));
        values.put(Constantes.COLUMN_TAREFA_SINCRONIZADA, tarefa.isSincronizada() ? 1 : 0);
        values.put(Constantes.COLUMN_TAREFA_STATUS, tarefa.getStatus().toString());
        values.put(Constantes.COLUMN_TAREFA_LATITUDE_CRIACAO, tarefa.getLatitudeCriacao());
        values.put(Constantes.COLUMN_TAREFA_LONGITUDE_CRIACAO, tarefa.getLongitudeCriacao());

        if (tarefa.isSincronizada()) {
            values.put(Constantes.COLUMN_TAREFA_TASKLY_ID, tarefa.getTasklyTaskId());
            values.put(Constantes.COLUMN_TAREFA_TASKLY_ACCOUNT, tarefa.getTasklyAccount());
        }

        try {
            long id = db.insertOrThrow(Constantes.TABLE_TAREFA, null, values);

            tarefa.setId(id);

            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            db.endTransaction();
            db.close();
        }
    }

    public boolean delete(Tarefa tarefa) {
        db = helper.getWritableDatabase();

        int rows = db.delete(Constantes.TABLE_TAREFA, "id = ? ", new String[] { String.valueOf(tarefa.getId()) });

        db.close();
        return rows > 0;
    }

    public void update(Tarefa tarefa) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constantes.COLUMN_TAREFA_DESCRICAO, tarefa.getDescricao());
        values.put(Constantes.COLUMN_TAREFA_DATA_LIMITE, Constantes.SQLITE_DATE_TIME_FORMAT.format(tarefa.getDataLimite()));
        values.put(Constantes.COLUMN_TAREFA_DATA_CRIACAO, Constantes.SQLITE_DATE_TIME_FORMAT.format(tarefa.getDataCriacao()));
        values.put(Constantes.COLUMN_TAREFA_SINCRONIZADA, tarefa.isSincronizada() ? 1 : 0);
        values.put(Constantes.COLUMN_TAREFA_STATUS, tarefa.getStatus().toString());
        values.put(Constantes.COLUMN_TAREFA_LATITUDE_CRIACAO, tarefa.getLatitudeCriacao());
        values.put(Constantes.COLUMN_TAREFA_LONGITUDE_CRIACAO, tarefa.getLongitudeCriacao());

        if (tarefa.isSincronizada()) {
            values.put(Constantes.COLUMN_TAREFA_TASKLY_ID, tarefa.getTasklyTaskId());
            values.put(Constantes.COLUMN_TAREFA_TASKLY_ACCOUNT, tarefa.getTasklyAccount());
        }

        db.update(Constantes.TABLE_TAREFA, values, "id = ?", new String[] { String.valueOf(tarefa.getId()) });

        db.close();
    }
}
