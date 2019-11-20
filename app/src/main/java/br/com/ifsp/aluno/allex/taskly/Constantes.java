package br.com.ifsp.aluno.allex.taskly;

import java.text.SimpleDateFormat;
import java.util.Locale;

public interface Constantes {

    String EXTRA_TAREFA = "TAREFA";

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt","BR"));
    SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm", new Locale("pt","BR"));
    SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("pt","BR"));

}
