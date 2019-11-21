package br.com.ifsp.aluno.allex.taskly;

import java.text.SimpleDateFormat;
import java.util.Locale;

public interface Constantes {

    String EXTRA_TAREFA = "TAREFA";

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt","BR"));
    SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm", new Locale("pt","BR"));
    SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("pt","BR"));
    SimpleDateFormat SQLITE_DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss", new Locale("pt","BR"));

    Integer DATABASE_VERSION = 1;
    String DATABASE_NAME = "tarefas.db";

    String TABLE_TAREFA = "tarefa";

    String COLUMN_TAREFA_ID = "id";
    String COLUMN_TAREFA_DESCRICAO = "descricao";
    String COLUMN_TAREFA_DATA = "data";
    String COLUMN_TAREFA_SINCRONIZADA = "sincronizada";
    String COLUMN_TAREFA_STATUS = "status";

    int DIAS_HOJE = 0;
    int DIAS_AMANHA = 1;
    int DIAS_SEMANA = 7;
    int DIAS_MES = 30;

    int REQ_CODE_GOOGLE_API_UNAVAILABLE = 0;

}
