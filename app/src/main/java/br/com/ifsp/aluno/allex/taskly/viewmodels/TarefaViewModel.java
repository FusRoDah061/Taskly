package br.com.ifsp.aluno.allex.taskly.viewmodels;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.text.ParseException;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public class TarefaViewModel extends BaseObservable {

    private Tarefa tarefa;

    @Bindable
    private String toastMessage = null;

    public TarefaViewModel(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    @Bindable
    public String getDescricaoTarefa() {
        return tarefa.getDescricao();
    }

    public void setDescricaoTarefa(String descricaoTarefa) {
        tarefa.setDescricao(descricaoTarefa);
        notifyPropertyChanged(BR.descricaoTarefa);
    }

    @Bindable
    public String getDataTarefa() {
        return Constantes.DATE_TIME_FORMAT.format(tarefa.getData());
    }

    public void setDataTarefa(String dataTarefa) {
        try {
            tarefa.setData(Constantes.DATE_FORMAT.parse(dataTarefa));
            notifyPropertyChanged(BR.dataTarefa);
        } catch (ParseException e) {
            setToastMessage("O formato da data não é válido. Utilize o formato DD/MM/AAAA HH:MM:SS");
            e.printStackTrace();
        }
    }

    public String getToastMessage() {
        return toastMessage;
    }

    public void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
        notifyPropertyChanged(BR.toastMessage);
    }

    public void onDataTarefaClicked() {
        //TODO: Abrir o datetime picker
    }

    public void onContinuarClicked() {
        if (isInputDataValid()) {
            //TODO: Ir para o próximo fragment
        }
    }

    public boolean isInputDataValid() {
        if(TextUtils.isEmpty(tarefa.getDescricao())){
            setToastMessage("A descrição da tarefa não pode ser vazia.");
            return false;
        }

        return true;
    }
}
