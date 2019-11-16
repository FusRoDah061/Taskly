package br.com.ifsp.aluno.allex.taskly.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SincronizarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SincronizarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
