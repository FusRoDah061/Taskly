package br.com.ifsp.aluno.allex.taskly.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.databinding.Bindable;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.Globals;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public class SincronizarViewModel extends BaseViewModel {

    private Globals globals = Globals.getInstance();

    private boolean naoPerguntarNovamente = false;

    public SincronizarViewModel(Tarefa tarefa, Activity activity) {
        super(tarefa, activity);
    }

    public void onSimClicked(){
        if(naoPerguntarNovamente) {
            globals.setPerguntarSincronizar(false);
            globals.setIndicaSincronizar(true);
        }

        //TODO: Mostrar na tela de sincronizar a conta padrão e não deixar sincronizar se for null
        SharedPreferences preferences = activity.getSharedPreferences(Constantes.PREF_NAME, Context.MODE_PRIVATE);
        String account = preferences.getString(Constantes.PREF_CONTA_PADRAO, null);

        tarefa.setGoogleAccount(account);
        tarefa.setSincronizada(true);
        goToNextFragment();
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
}
