package br.com.ifsp.aluno.allex.taskly.viewmodels;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public class SincronizarViewModel extends ViewModel {

    private MutableLiveData<Event<Boolean>> nextFragmentEvent = new MutableLiveData<>();

    private Tarefa tarefa;
    private Context context;

    public SincronizarViewModel(Tarefa tarefa, FragmentActivity activity) {
        this.tarefa = tarefa;
        this.context = context;
    }

    public MutableLiveData onNextFragmentEvent(){
        return nextFragmentEvent;
    }

    public void onSimClicked(){
        nextFragmentEvent.postValue(new Event<Boolean>(true));
    }

    public void onNaoClicked() {
        nextFragmentEvent.postValue(new Event<Boolean>(true));
    }

}
