package br.com.ifsp.aluno.allex.taskly.google.services.tasks;

import android.os.AsyncTask;

import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;

import java.io.IOException;

import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.events.OnAsyncTaskFinishListener;
import br.com.ifsp.aluno.allex.taskly.google.services.GoogleCommons;
import br.com.ifsp.aluno.allex.taskly.views.AsyncActivity;

abstract class CalendarAsyncTask extends AsyncTask<Void, Void, Boolean> {

    protected final AsyncActivity activity;
    protected final Calendar client;

    private OnAsyncTaskFinishListener onAsyncTaskFinishListener;
    private Exception error;

    public CalendarAsyncTask(AsyncActivity activity, Calendar client) {
        this.activity = activity;
        this.client = client;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.newAsyncTasks();
    }

    @Override
    protected final Boolean doInBackground(Void... ignored) {
        GoogleCommons googleCommons = new GoogleCommons();

        try {
            doInBackground();
            return true;
        } catch (final GooglePlayServicesAvailabilityIOException availabilityException) {
            error = availabilityException;
            googleCommons.showGooglePlayServicesAvailabilityErrorDialog(activity, availabilityException.getConnectionStatusCode(), activity);
        } catch (UserRecoverableAuthIOException userRecoverableException) {
            error = userRecoverableException;
            googleCommons.showAuthorizationRequest(activity, userRecoverableException.getIntent());
        } catch (Exception e) {
            error = e;
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected final void onPostExecute(Boolean success) {
        super.onPostExecute(success);

        activity.asyncTaskFinished();

        if(onAsyncTaskFinishListener != null)
            onAsyncTaskFinishListener.onAsyncTaskFinished(success, this.error);
    }

    protected String getTasklyCalendarId() {
        //TODO: Buscar o calendário padrão taskly na conta
        //TODO: Buscar calendário taskly ou criar novo. Deve rodar nas asyncTasks
        try {

            String tasklyCalendarName = activity.getResources().getString(R.string.app_name);
            CalendarList feed = client.calendarList().list().setFields("items(id,summary,description)").execute();
            CalendarListEntry tasklyDefault = null;

            for(CalendarListEntry calendar : feed.getItems()) {
                if(tasklyCalendarName.equals(calendar.getSummary())) {
                    tasklyDefault = calendar;
                    break;
                }
            }

            if(tasklyDefault == null) {
                return createTasklyCalendar();
            }

            return tasklyDefault.getId();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String createTasklyCalendar() {
        try {
            com.google.api.services.calendar.model.Calendar taskly = new com.google.api.services.calendar.model.Calendar();
            taskly.setSummary(activity.getResources().getString(R.string.app_name));
            taskly.setDescription(activity.getResources().getString(R.string.str_taskly_calendar_description));

            client.calendars().insert(taskly).execute().getId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    abstract protected void doInBackground() throws IOException;

    public void setOnAsyncTaskFinishListener(OnAsyncTaskFinishListener onAsyncTaskFinishListener) {
        this.onAsyncTaskFinishListener = onAsyncTaskFinishListener;
    }
}
