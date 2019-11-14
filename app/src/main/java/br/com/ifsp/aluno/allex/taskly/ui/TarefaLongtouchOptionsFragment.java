package br.com.ifsp.aluno.allex.taskly.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import br.com.ifsp.aluno.allex.taskly.R;

public class TarefaLongtouchOptionsFragment extends BottomSheetDialogFragment {

    static TarefaLongtouchOptionsFragment newInstance() {
        return new TarefaLongtouchOptionsFragment();
    }

    @Override public int getTheme() {
        return  R.style.Theme_MaterialComponents_Light_BottomSheetDialog;
    }

    @Nullable
    @Override public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_tarefa_longtouch_options_content, container, false);
    }

}
