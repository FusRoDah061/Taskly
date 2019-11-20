package br.com.ifsp.aluno.allex.taskly.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.events.OnTarefaActionListener;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public class TarefaLongtouchOptionsFragment extends BottomSheetDialogFragment implements NavigationView.OnNavigationItemSelectedListener {

    private Tarefa tarefa;
    private OnTarefaActionListener onTarefaActionListener;

    @Nullable
    @Override public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View root = inflater.inflate(R.layout.fragment_tarefa_longtouch_options_content, container, false);

        Bundle args = getArguments();

        if(args != null) {
            tarefa = (Tarefa) args.getSerializable(Constantes.EXTRA_TAREFA);
        }

        NavigationView navigationView = (NavigationView) root.findViewById (R.id.nav_options);
        navigationView.setNavigationItemSelectedListener(this);

        return root;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.option_editar) {
            if(onTarefaActionListener != null)
                onTarefaActionListener.onEditarTarefa(tarefa);

        } else if (id == R.id.option_excluir) {
            if(onTarefaActionListener != null)
                onTarefaActionListener.onExcluirTarefa(tarefa);
        }

        dismiss();

        return true;
    }

    public void setOnTarefaActionListener(OnTarefaActionListener onTarefaActionListener) {
        this.onTarefaActionListener = onTarefaActionListener;
    }

}
