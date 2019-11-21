package br.com.ifsp.aluno.allex.taskly.views.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.databinding.FragmentSincronizarBinding;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.viewmodels.SincronizarViewModel;
import br.com.ifsp.aluno.allex.taskly.views.NovaTarefaActivity;

public class SincronizarFragment extends BaseFragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentSincronizarBinding bind = DataBindingUtil.inflate(inflater, R.layout.fragment_sincronizar, container, false);
        View root = bind.getRoot();

        activity = (NovaTarefaActivity) getActivity();
        Tarefa tarefa = activity.getTarefa();
        viewModel = new SincronizarViewModel(tarefa, activity);

        initViewModelSequence(viewModel);

        bind.setViewModel((SincronizarViewModel) viewModel);
        bind.executePendingBindings();

        return root;
    }

}
