package br.com.ifsp.aluno.allex.taskly.google.services;

import android.app.Activity;
import android.app.Dialog;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import android.content.DialogInterface.OnCancelListener;

import br.com.ifsp.aluno.allex.taskly.Constantes;

public class GoogleCommons {

    private int connectionResult;

    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(activity);

        connectionResult = result;

        if(result == ConnectionResult.SUCCESS) {
            return true;
        }
        else if(result == ConnectionResult.SERVICE_MISSING ||
                result == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED ||
                result == ConnectionResult.SERVICE_DISABLED) {
            return false;
        }

        return false;
    }

    public void showGooglePlayServicesAvailabilityErrorDialog(Activity activity, int connectionResult, OnCancelListener onCancelListener) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = googleApiAvailability.getErrorDialog(activity, connectionResult, Constantes.REQ_CODE_GOOGLE_API_UNAVAILABLE, onCancelListener);
        dialog.show();
    }

    public int getConnectionResult() {
        return connectionResult;
    }
}
