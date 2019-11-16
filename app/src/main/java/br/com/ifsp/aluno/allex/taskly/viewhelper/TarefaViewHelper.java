package br.com.ifsp.aluno.allex.taskly.viewhelper;

import android.content.Context;
import android.widget.ArrayAdapter;

import br.com.ifsp.aluno.allex.taskly.R;

public class TarefaViewHelper {

    public ArrayAdapter<CharSequence> getDiasSpinnerAdapter (Context context){
        ArrayAdapter<CharSequence> adapterDia = ArrayAdapter.createFromResource(context,
                R.array.dias_array, android.R.layout.simple_spinner_item);

        adapterDia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapterDia;
    }

    public ArrayAdapter<CharSequence> getHorasSpinnerAdapter (Context context) {
        ArrayAdapter<CharSequence> adapterHora = ArrayAdapter.createFromResource(context,
                R.array.hora_array, android.R.layout.simple_spinner_item);

        adapterHora.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapterHora;
    }

}
