package br.com.ifsp.aluno.allex.taskly.viewmodels;

import androidx.databinding.Bindable;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.views.AsyncActivity;

public class TarefaPropriedadesViewModel extends BaseViewModel {

    public TarefaPropriedadesViewModel(Tarefa tarefa, AsyncActivity activity) {
        super(tarefa, activity);
    }

    @Bindable
    public String getDescricao() {
        return tarefa.getDescricao();
    }

    @Bindable
    public String getDataCriacao() {
        return Constantes.DATE_TIME_FORMAT.format(tarefa.getDataCriacao());
    }

    @Bindable
    public String getDataLimite() {
        return Constantes.DATE_TIME_FORMAT.format(tarefa.getDataLimite());
    }

}
