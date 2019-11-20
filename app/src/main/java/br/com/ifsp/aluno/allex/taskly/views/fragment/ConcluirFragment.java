package br.com.ifsp.aluno.allex.taskly.views.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.databinding.FragmentConcluirBinding;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.viewmodels.ConcluirViewModel;
import br.com.ifsp.aluno.allex.taskly.viewmodels.Event;
import br.com.ifsp.aluno.allex.taskly.views.NovaTarefaActivity;

public class ConcluirFragment extends Fragment {

    private ConcluirViewModel concluirViewModel;
    private NovaTarefaActivity activity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentConcluirBinding bind = DataBindingUtil.inflate(inflater, R.layout.fragment_concluir, container, false);
        View root = bind.getRoot();

        activity = (NovaTarefaActivity) getActivity();
        Tarefa tarefa = activity.getTarefa();
        concluirViewModel = new ConcluirViewModel(tarefa, getActivity());

        concluirViewModel.onNextFragmentEvent().observe(this, new Observer() {
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

        bind.setViewModel(concluirViewModel);
        bind.executePendingBindings();

        return root;
    }

}
