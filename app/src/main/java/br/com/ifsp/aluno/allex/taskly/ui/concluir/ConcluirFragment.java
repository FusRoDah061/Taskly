package br.com.ifsp.aluno.allex.taskly.ui.concluir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import br.com.ifsp.aluno.allex.taskly.R;

public class ConcluirFragment extends Fragment {

    private ConcluirViewModel concluirViewModel;
    private View root;

    private TextView tvDescricaoTarefa;
    private TextView tvDataTarefa;
    private TextView tvHoraTarefa;
    private Button   btnFinalizar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        concluirViewModel =
                ViewModelProviders.of(this).get(ConcluirViewModel.class);
        root = inflater.inflate(R.layout.fragment_concluir, container, false);

        initComponents();

        return root;
    }

    private void initComponents() {
        tvDescricaoTarefa = (TextView) root.findViewById(R.id.tvDescricaoTarefa);
        tvDataTarefa      = (TextView) root.findViewById(R.id.tvDataTarefa);
        tvHoraTarefa      = (TextView) root.findViewById(R.id.tvHoraTarefa);
        btnFinalizar      = (Button)   root.findViewById(R.id.btnFinalizar);
    }
}
