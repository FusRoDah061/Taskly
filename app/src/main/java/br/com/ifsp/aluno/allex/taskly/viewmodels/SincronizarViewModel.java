package br.com.ifsp.aluno.allex.taskly.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.Globals;
import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.ui.InputDialog;

public class SincronizarViewModel extends BaseViewModel {

    private Globals globals = Globals.getInstance();
    private SharedPreferences preferences;

    private boolean naoPerguntarNovamente = false;

    private String contaSincronizacao;

    public SincronizarViewModel(Tarefa tarefa, Activity activity) {
        super(tarefa, activity);
        preferences = activity.getSharedPreferences(Constantes.PREF_NAME, Context.MODE_PRIVATE);
        setContaSincronizacao(preferences.getString(Constantes.PREF_CONTA_PADRAO, null));
    }

    public void onSimClicked(){
        if(naoPerguntarNovamente) {
            globals.setPerguntarSincronizar(false);
            globals.setIndicaSincronizar(true);

            if(!TextUtils.isEmpty(contaSincronizacao)) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Constantes.PREF_CONTA_PADRAO, contaSincronizacao);
                editor.commit();
            }
        }

        if(contaSincronizacao == null) {
            final InputDialog inputDialog = new InputDialog(activity);
            inputDialog
                    .setTitle(activity.getResources().getString(R.string.str_input_dialog_add_conta_title))
                    .setPlaceholder(activity.getResources().getString(R.string.str_input_dialog_add_conta_placeholder))
                    .setPositiveButton(activity.getResources().getString(R.string.str_input_dialog_add_conta_posbutton), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String email = inputDialog.getValue();

                            if(!TextUtils.isEmpty(email)) {
                                setContaSincronizacao(email);
                                tarefa.setTasklyAccount(email);
                            }
                            else {
                                Toast.makeText(activity, activity.getResources().getString(R.string.str_input_dialog_add_conta_noaccount_sync), Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton(activity.getResources().getString(R.string.str_input_dialog_add_conta_negbutton), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(activity, activity.getResources().getString(R.string.str_input_dialog_add_conta_noaccount_sync), Toast.LENGTH_LONG).show();
                        }
                    })
                    .show();
        }
        else {
            tarefa.setSincronizada(true);
            goToNextFragment();
        }
    }

    public void onNaoClicked() {
        if(naoPerguntarNovamente) {
            globals.setPerguntarSincronizar(false);
            globals.setIndicaSincronizar(false);
        }

        tarefa.setSincronizada(false);
        goToNextFragment();
    }

    @Bindable
    public boolean getNaoPerguntarNovamente() {
        return naoPerguntarNovamente;
    }

    public void setNaoPerguntarNovamente(boolean naoPerguntarNovamente) {
        this.naoPerguntarNovamente = naoPerguntarNovamente;
    }

    @Bindable
    public String getContaSincronizacao() {
        return contaSincronizacao;
    }

    public void setContaSincronizacao(String contaSincronizacao) {
        this.contaSincronizacao = contaSincronizacao;
        notifyPropertyChanged(BR.contaSincronizacao);
    }
}
