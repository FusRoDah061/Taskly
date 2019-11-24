package br.com.ifsp.aluno.allex.taskly.google.services;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.aluno.allex.taskly.Constantes;

public class GoogleAccountManager {

    private Activity activity;
    private GoogleAccountCredential credential;

    public GoogleAccountManager(Activity activity){
        this.activity = activity;

        List<String> scopes = new ArrayList<>(2);
        scopes.add(GoogleCalendarScopes.CALENDAR);
        scopes.add(GoogleCalendarScopes.EVENTS);

        credential = GoogleAccountCredential.usingOAuth2(activity, scopes);
        credential.setSelectedAccountName(null);
    }

    public void showAccoutPicker() {
        activity.startActivityForResult(credential.newChooseAccountIntent(), Constantes.REQ_CODE_DIALOG_CONTA_GOOGLE);
    }

    public void setDefaultAccountName(String accountName) {
        SharedPreferences settings = activity.getSharedPreferences(Constantes.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Constantes.PREF_CONTA_PADRAO, accountName);
        editor.apply();

        setAccountName(accountName);
    }

    public void setAccountName(String accountName) {
        credential.setSelectedAccountName(accountName);
    }

    public GoogleAccountCredential getCredential() {
        return credential;
    }
}
