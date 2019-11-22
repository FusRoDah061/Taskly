package br.com.ifsp.aluno.allex.taskly.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.persistence.repository.TarefaRepository;

public class BootReceiver extends BroadcastReceiver {
    //Referência: https://stackoverflow.com/questions/36902667/how-to-schedule-notification-in-android

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            TarefaNotificationReceiver tarefaNotificationReceiver = new TarefaNotificationReceiver();
            TarefaRepository tarefaRepository = new TarefaRepository(context);
            List<Tarefa> tarefas = tarefaRepository.findAllFromDate(new Date());

            for (Tarefa tarefa : tarefas) {
                tarefaNotificationReceiver.scheduleNotification(context, tarefa);
            }
        }
    }
}
