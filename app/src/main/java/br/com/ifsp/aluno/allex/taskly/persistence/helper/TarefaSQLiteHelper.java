package br.com.ifsp.aluno.allex.taskly.persistence.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import br.com.ifsp.aluno.allex.taskly.Constantes;

public class TarefaSQLiteHelper extends SQLiteOpenHelper {


    public TarefaSQLiteHelper(@Nullable Context context) {
        super(context, Constantes.DATABASE_NAME, null, Constantes.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { 
        createTarefasTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTarefasTable(db);

        this.onCreate(db);
    }

    private void dropTarefasTable(SQLiteDatabase db) {
        db.execSQL("drop table if exists tarefa");
    }

    private void createTarefasTable(SQLiteDatabase db) {
        String sql = "create table if not exists tarefa(" +
                     "  id integer primary key autoincrement," +
                     "  descricao varchar(100) not null," +
                     "  data varchar(15) not null," +
                     "  sincronizada integer not null default 0," +
                     "  status varchar(10) not null default 'PENDENTE'," +
                     "  taskly_id integer);";

        db.execSQL(sql);
    }

}
