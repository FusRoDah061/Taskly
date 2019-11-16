package br.com.ifsp.aluno.allex.taskly.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConcluirViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ConcluirViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
