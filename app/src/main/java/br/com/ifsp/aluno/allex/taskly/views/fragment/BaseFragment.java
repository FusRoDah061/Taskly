package br.com.ifsp.aluno.allex.taskly.views.fragment;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import br.com.ifsp.aluno.allex.taskly.viewmodels.BaseViewModel;
import br.com.ifsp.aluno.allex.taskly.viewmodels.Event;
import br.com.ifsp.aluno.allex.taskly.views.NovaTarefaActivity;

public abstract class BaseFragment extends Fragment {

    protected BaseViewModel viewModel;
    protected NovaTarefaActivity activity;

    protected void initViewModelSequence(BaseViewModel viewModel) {
        viewModel.onNextFragmentEvent().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Event<Boolean> event = (Event<Boolean>) o;

                if(!event.hasBeenHandled()) {
                    Boolean goNext = event.getContentIfNotHandledOrReturnNull();

                    if(goNext)
                        activity.nextFragment();
                }
            }
        });
    }
}
