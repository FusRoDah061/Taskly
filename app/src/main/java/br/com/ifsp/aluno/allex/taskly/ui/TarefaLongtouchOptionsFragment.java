package br.com.ifsp.aluno.allex.taskly.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public class TarefaLongtouchOptionsFragment extends BottomSheetDialogFragment {

    private Tarefa tarefa;

    @Nullable
    @Override public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        Bundle args = getArguments();

        if(args != null) {
            Tarefa tarefa = (Tarefa) args.getSerializable("TAREFA");
        }

        return inflater.inflate(R.layout.fragment_tarefa_longtouch_options_content, container, false);
    }

}
