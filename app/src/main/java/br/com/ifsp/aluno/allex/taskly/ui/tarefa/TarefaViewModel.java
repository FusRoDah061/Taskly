package br.com.ifsp.aluno.allex.taskly.ui.tarefa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TarefaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TarefaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
