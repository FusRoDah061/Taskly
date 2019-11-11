package br.com.ifsp.aluno.allex.taskly.ui.tarefa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import br.com.ifsp.aluno.allex.taskly.R;

public class TarefaFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private TarefaViewModel tarefaViewModel;
    private View root;

    private EditText edtDescricaoTarefa;
    private Spinner  spDiaTarefa;
    private Spinner  spHoraTarefa;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tarefaViewModel = ViewModelProviders.of(this).get(TarefaViewModel.class);
        root = inflater.inflate(R.layout.fragment_tarefa, container, false);

        initComponents();

        return root;
    }

    private void initComponents(){
        edtDescricaoTarefa = (EditText) root.findViewById(R.id.edtDescricaoTarefa);
        spDiaTarefa        = (Spinner)  root.findViewById(R.id.spDiaTarefa);
        spHoraTarefa       = (Spinner)  root.findViewById(R.id.spHoraTarefa);

        ArrayAdapter<CharSequence> adapterDia = ArrayAdapter.createFromResource(getActivity(),
                R.array.dias_array, android.R.layout.simple_spinner_item);
        adapterDia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDiaTarefa.setAdapter(adapterDia);
        spDiaTarefa.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterHora = ArrayAdapter.createFromResource(getActivity(),
                R.array.hora_array, android.R.layout.simple_spinner_item);
        adapterHora.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHoraTarefa.setAdapter(adapterHora);
        spHoraTarefa.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
