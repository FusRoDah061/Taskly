package br.com.ifsp.aluno.allex.taskly.viewmodels;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

public abstract class SequenceViewModel extends BaseObservable {

    private MutableLiveData<Event<Boolean>> nextFragmentEvent = new MutableLiveData<>();

    public MutableLiveData onNextFragmentEvent(){
        return nextFragmentEvent;
    }

    void goToNextFragment() {
        nextFragmentEvent.postValue(new Event<Boolean>(true));
    }
}
