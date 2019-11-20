package br.com.ifsp.aluno.allex.taskly;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constantes {

    public final static String EXTRA_TAREFA            = "TAREFA";

    public final static SimpleDateFormat DATE_FORMAT   = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt","BR"));
    public final static SimpleDateFormat TIME_FORMAT   = new SimpleDateFormat("HH:mm", new Locale("pt","BR"));
    public final static SimpleDateFormat DATE_TIME_FORMAT   = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("pt","BR"));

}
