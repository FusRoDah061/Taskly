package br.com.ifsp.aluno.allex.taskly.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;

public class BootReceiver extends BroadcastReceiver {
    //Referência: https://stackoverflow.com/questions/36902667/how-to-schedule-notification-in-android

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            intent = new Intent(context, TarefaNotificationReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            // TODO: use calendar.add(Calendar.SECOND,MINUTE,HOUR, int);
            //calendar.add(Calendar.SECOND, 10);

            //TODO: Buscar tarefas para setar as notificações

            //ALWAYS recompute the calendar after using add, set, roll
            Date date = calendar.getTime();

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), alarmIntent);
        }
    }
}
