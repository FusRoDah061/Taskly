package br.com.ifsp.aluno.allex.taskly.ui.sincronizar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import br.com.ifsp.aluno.allex.taskly.R;

public class SincronizarFragment extends Fragment {

    private SincronizarViewModel sincronizarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sincronizarViewModel =
                ViewModelProviders.of(this).get(SincronizarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sincronizar, container, false);

        /*final TextView textView = root.findViewById(R.id.text_dashboard);
        sincronizarViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        return root;
    }
}
