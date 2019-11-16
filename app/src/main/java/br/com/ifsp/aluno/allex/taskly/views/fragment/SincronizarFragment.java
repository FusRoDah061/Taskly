package br.com.ifsp.aluno.allex.taskly.views.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.viewmodels.SincronizarViewModel;

public class SincronizarFragment extends Fragment {

    private SincronizarViewModel sincronizarViewModel;
    private View root;

    private Button   btnSim;
    private Button   btnNao;
    private CheckBox cbNaoPerguntar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sincronizarViewModel =
                ViewModelProviders.of(this).get(SincronizarViewModel.class);
        root = inflater.inflate(R.layout.fragment_sincronizar, container, false);

        /*final TextView textView = root.findViewById(R.id.text_dashboard);
        sincronizarViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        initComponents();

        return root;
    }

    private void initComponents() {
        btnSim         = (Button)   root.findViewById(R.id.btnSim);
        btnNao         = (Button)   root.findViewById(R.id.btnNao);
        cbNaoPerguntar = (CheckBox) root.findViewById(R.id.cbNaoPerguntar);
    }
}
