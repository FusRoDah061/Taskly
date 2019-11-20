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
        createContaGoogleTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTarefasTable(db);
        dropContaGoogleTable(db);

        this.onCreate(db);
    }

    private void dropContaGoogleTable(SQLiteDatabase db) {
        db.execSQL("drop table if exists conta_google");
    }

    private void dropTarefasTable(SQLiteDatabase db) {
        db.execSQL("drop table if exists tarefa");
    }

    private void createTarefasTable(SQLiteDatabase db) {
        String sql = "create table if not exists tarefa(" +
                     "  id integer primary key autoincrement," +
                     "  descricao varchar(100) not null," +
                     "  data timestamp not null," +
                     "  sincronizada integer not null default 0," +
                     "  status varchar(10) not null default 'PENDENTE');";

        db.execSQL(sql);
    }

    private void createContaGoogleTable(SQLiteDatabase db) {
        String sql = "create table if not exists conta_google(" +
                     "  id varchar(255) primary key);";

        //TODO: Criar tabela de conta google

        db.execSQL(sql);
    }
}
