package br.com.ifsp.aluno.allex.taskly.viewmodels;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import br.com.ifsp.aluno.allex.taskly.Globals;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public class SincronizarViewModel extends BaseObservable {

    private MutableLiveData<Event<Boolean>> nextFragmentEvent = new MutableLiveData<>();

    private Globals globals = Globals.getInstance();

    private Tarefa tarefa;
    private Context context;

    private boolean naoPerguntarNovamente = false;

    public SincronizarViewModel(Tarefa tarefa, FragmentActivity activity) {
        this.tarefa = tarefa;
        this.context = context;
    }

    public MutableLiveData onNextFragmentEvent(){
        return nextFragmentEvent;
    }

    public void onSimClicked(){
        //TODO: Perguntar conta google

        if(naoPerguntarNovamente) {
            globals.setPerguntarSincronizar(false);
            globals.setIndicaSincronizar(true);

            //TODO: Salvar conta google padrão
        }

        tarefa.setSincronizada(true);
        proximoFragment();
    }

    public void onNaoClicked() {
        if(naoPerguntarNovamente) {
            globals.setPerguntarSincronizar(false);
            globals.setIndicaSincronizar(false);
        }

        tarefa.setSincronizada(false);
        proximoFragment();
    }

    private void proximoFragment() {
        nextFragmentEvent.postValue(new Event<Boolean>(true));
    }

    @Bindable
    public boolean getNaoPerguntarNovamente() {
        return naoPerguntarNovamente;
    }

    public void setNaoPerguntarNovamente(boolean naoPerguntarNovamente) {
        this.naoPerguntarNovamente = naoPerguntarNovamente;
    }
}
