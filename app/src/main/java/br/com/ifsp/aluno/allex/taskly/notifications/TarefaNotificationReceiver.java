package br.com.ifsp.aluno.allex.taskly.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

import java.util.Calendar;
import java.util.Date;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.enums.EStatusTarefa;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.persistence.repository.TarefaRepository;

public class TarefaNotificationReceiver extends WakefulBroadcastReceiver {

    private AlarmManager mAlarmManager;

    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();

        if(extras != null) {
            long tarefaId = extras.getLong(Constantes.EXTRA_TAREFA);

            if(tarefaId > 0) {
                TarefaRepository tarefaRepository = new TarefaRepository(context);
                Tarefa tarefa = tarefaRepository.findById(tarefaId);

                if(tarefa.getStatus() == EStatusTarefa.PENDENTE) {

                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, Constantes.NOTIFICATION_CHANNEL_ID);
                    notificationBuilder.setSmallIcon(R.drawable.ic_event_note_black_24dp)
                            .setContentTitle(tarefa.getDescricao())
                            .setContentText(String.format("Tarefa agendada para as %s.", Constantes.DATE_TIME_FORMAT.format(tarefa.getData())));

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.notify((int) tarefaId, notificationBuilder.build());
                }
            }
        }

        TarefaNotificationReceiver.completeWakefulIntent(intent);
    }

    public void scheduleNotification(Context context, Tarefa tarefa) {
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TarefaNotificationReceiver.class);
        intent.putExtra(Constantes.EXTRA_TAREFA, tarefa.getId());

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, tarefa.getId().intValue(), intent, 0);

        if(tarefa.getData().compareTo(new Date()) < 0)
            return;

        if(tarefa.getStatus() == EStatusTarefa.CONCLUIDA)
            return;

        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, tarefa.getData().getTime(), alarmIntent);

        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public void cancelNotification(Context context, Tarefa tarefa) {
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TarefaNotificationReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, tarefa.getId().intValue(), intent, 0);

        mAlarmManager.cancel(alarmIntent);

        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
