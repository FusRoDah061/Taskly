package br.com.ifsp.aluno.allex.taskly.google.services;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.calendar.CalendarScopes;

import java.util.Collections;

import br.com.ifsp.aluno.allex.taskly.Constantes;

public class GoogleAccountManager {

    private Activity activity;
    private GoogleAccountCredential credential;

    public GoogleAccountManager(Activity activity){
        this.activity = activity;

        credential = GoogleAccountCredential.usingOAuth2(activity, Collections.singleton(CalendarScopes.CALENDAR));
        credential.setSelectedAccountName(null);
    }

    public void showAccoutPicker() {
        activity.startActivityForResult(credential.newChooseAccountIntent(), Constantes.REQ_CODE_DIALOG_CONTA_GOOGLE);
    }

    public void setDefaultAccountName(String accountName) {
        SharedPreferences settings = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Constantes.PREF_CONTA_PADRAO, accountName);
        editor.apply();
    }

}
