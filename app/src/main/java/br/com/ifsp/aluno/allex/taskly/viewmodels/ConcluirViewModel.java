package br.com.ifsp.aluno.allex.taskly.viewmodels;

import android.content.Context;

import androidx.databinding.Bindable;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.persistence.repository.TarefaRepository;

public class ConcluirViewModel extends BaseViewModel {

    public ConcluirViewModel(Tarefa tarefa, Context context) {
        super(tarefa, context);
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

        goToNextFragment();
    }

}
