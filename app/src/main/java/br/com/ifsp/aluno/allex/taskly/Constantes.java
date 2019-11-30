package br.com.ifsp.aluno.allex.taskly;

import java.text.SimpleDateFormat;
import java.util.Locale;

public interface Constantes {

    String EXTRA_TAREFA = "TAREFA";
    String EXTRA_MAPVIEW = "EXTRA_MAPVIEW";

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt","BR"));
    SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm", new Locale("pt","BR"));
    SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("pt","BR"));
    SimpleDateFormat SQLITE_DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss", new Locale("pt","BR"));
    SimpleDateFormat TASKLY_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("pt","BR"));

    Integer DATABASE_VERSION = 1;
    String DATABASE_NAME = "tarefas.db";

    String TABLE_TAREFA = "tarefa";

    String COLUMN_TAREFA_ID = "id";
    String COLUMN_TAREFA_DESCRICAO = "descricao";
    String COLUMN_TAREFA_DATA_LIMITE = "data_limite";
    String COLUMN_TAREFA_DATA_CRIACAO = "data_criacao";
    String COLUMN_TAREFA_LATITUDE_CRIACAO = "latitude_criacao";
    String COLUMN_TAREFA_LONGITUDE_CRIACAO = "longitude_criacao";
    String COLUMN_TAREFA_SINCRONIZADA = "sincronizada";
    String COLUMN_TAREFA_STATUS = "status";
    String COLUMN_TAREFA_TASKLY_ID= "taskly_id";
    String COLUMN_TAREFA_TASKLY_ACCOUNT= "taskly_account";

    int DIAS_HOJE = 0;
    int DIAS_AMANHA = 1;
    int DIAS_SEMANA = 7;
    int DIAS_MES = 30;

    String NOTIFICATION_CHANNEL_ID = "TASKLY_NOTIFICATION_CHANNEL_ID";

    String PREF_NAME = "TASKLY_PREFS";
    String PREF_CONTA_PADRAO = "PREF_CONTA_PADRAO";

    String URL_TASKLY_BASE = "http://taskly-web.herokuapp.com/";
    String URL_ENDPOINT_TASKLY_TAREFAS = URL_TASKLY_BASE + "api/tarefas";
    String URL_ENDPOINT_TASKLY_TAREFA = URL_TASKLY_BASE + "api/tarefa";

    int HTTP_REQUEST_TIMEOUT = 60000;
}
