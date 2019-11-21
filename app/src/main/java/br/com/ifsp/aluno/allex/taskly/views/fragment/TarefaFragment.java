package br.com.ifsp.aluno.allex.taskly.views.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.databinding.FragmentTarefaBinding;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.viewhelper.TarefaViewHelper;
import br.com.ifsp.aluno.allex.taskly.viewmodels.TarefaViewModel;
import br.com.ifsp.aluno.allex.taskly.views.NovaTarefaActivity;

public class TarefaFragment extends BaseFragment {

    private final TarefaViewHelper tarefaViewHelper = new TarefaViewHelper();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentTarefaBinding bind = DataBindingUtil.inflate(inflater, R.layout.fragment_tarefa, container, false);
        View root = bind.getRoot();

        activity = (NovaTarefaActivity) getActivity();
        Tarefa tarefa = activity.getTarefa();
        viewModel = new TarefaViewModel(tarefa, activity);

        initViewModelSequence(viewModel);

        bind.setViewModel((TarefaViewModel) viewModel);
        bind.executePendingBindings();

        return root;
    }

    @BindingAdapter({"toastMessage"})
    public static void toastMessage(View view, String message) {
        if (message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
