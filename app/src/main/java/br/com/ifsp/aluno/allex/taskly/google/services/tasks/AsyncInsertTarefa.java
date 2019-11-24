package br.com.ifsp.aluno.allex.taskly.google.services.tasks;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.util.TimeZone;

import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.views.AsyncActivity;

public class AsyncInsertTarefa extends TarefaAsyncTask {

    public AsyncInsertTarefa(AsyncActivity activity, Calendar client, Tarefa tarefa) {
        super(activity, client, tarefa);
    }

    @Override
    protected void doInBackground() throws IOException {
        //TODO: Inserir tarefa
        String calendarId = getTasklyCalendarId();

        Event event = new Event();
        EventDateTime startDate = new EventDateTime();
        EventDateTime endDate = new EventDateTime();

        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(tarefa.getData());
        c.add(java.util.Calendar.MINUTE, 30);

        startDate.setDateTime(new DateTime(tarefa.getData(), TimeZone.getDefault()));
        endDate.setDateTime(new DateTime(c.getTime(), TimeZone.getDefault()));

        event.setDescription(tarefa.getDescricao());
        event.setStart(startDate);
        event.setEnd(endDate);

        Event created = client.events().insert(calendarId, event).execute();

        tarefa.setGoogleCalendarId(calendarId);
        tarefa.setGoogleCalendarTaskId(created.getId());
    }
}
