package br.com.ifsp.aluno.allex.taskly.viewmodels;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.persistence.repository.TarefaRepository;

public class ConcluirViewModel extends BaseObservable {

    private MutableLiveData<Event<Boolean>> nextFragmentEvent = new MutableLiveData<>();

    private Tarefa tarefa;
    private Context context;

    public ConcluirViewModel(Tarefa tarefa, Context context) {
        this.tarefa = tarefa;
        this.context = context;
    }

    public MutableLiveData onNextFragmentEvent(){
        return nextFragmentEvent;
    }

    @Bindable
    public String getDescricaoTarefa() {
        return tarefa.getDescricao();
    }

    @Bindable
    public String getHoraTarefa() {
        return Constantes.TIME_FORMAT.format(tarefa.getData());
    }

    @Bindable
    public String getDataTarefa() {
        return Constantes.DATE_FORMAT.format(tarefa.getData());
    }

    @Bindable
    public String getIsSincronizada() {
        if(tarefa.isSincronizada())
            return "Ah, e sua tarefa será sincronizada com sua agenda Google!";
        else
            return "";
    }

    public void onLegalClicked() {
        new TarefaRepository(context).save(tarefa);

        if(tarefa.isSincronizada()){
            //TODO: Sincronizar tarefa
        }

        //TODO: Registrar notificação

        nextFragmentEvent.postValue(new Event<Boolean>(true));
    }

}
