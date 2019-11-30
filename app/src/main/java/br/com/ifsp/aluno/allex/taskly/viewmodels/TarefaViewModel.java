package br.com.ifsp.aluno.allex.taskly.viewmodels;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.views.AsyncActivity;

public class TarefaViewModel extends BaseViewModel {

    private String dataTarefa;
    private String horaTarefa;

    @Bindable
    private String toastMessage = null;

    public TarefaViewModel(Tarefa tarefa, AsyncActivity activity) {
        super(tarefa, activity);

        dataTarefa = Constantes.DATE_FORMAT.format(tarefa.getDataLimite());
        horaTarefa = Constantes.TIME_FORMAT.format(tarefa.getDataLimite());
    }

    @Bindable
    public String getDescricaoTarefa() {
        return tarefa.getDescricao();
    }

    public void setDescricaoTarefa(String descricaoTarefa) {
        if(!descricaoTarefa.equals(tarefa.getDescricao())) {
            tarefa.setDescricao(descricaoTarefa);
            notifyPropertyChanged(BR.descricaoTarefa);
        }
    }

    @Bindable
    public String getDataTarefa() {
        return this.dataTarefa;
    }

    public void setDataTarefa(String dataTarefa) {
        if(!dataTarefa.equals(this.dataTarefa)) {
            this.dataTarefa = dataTarefa;
            notifyPropertyChanged(BR.dataTarefa);
        }
    }

    @Bindable
    public String getHoraTarefa() {
        return horaTarefa;
    }

    public void setHoraTarefa(String horaTarefa) {
        if(!horaTarefa.equals(this.horaTarefa)) {
            this.horaTarefa = horaTarefa;
            notifyPropertyChanged(BR.horaTarefa);
        }
    }

    public String getToastMessage() {
        return toastMessage;
    }

    public void setToastMessage(String toastMessage) {
        if(!toastMessage.equals(this.toastMessage)) {
            this.toastMessage = toastMessage;
            notifyPropertyChanged(BR.toastMessage);
        }
    }

    public void onDataTarefaClicked() {
        Calendar c = Calendar.getInstance();

        try {
            c.setTime(Constantes.DATE_FORMAT.parse(dataTarefa));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DatePickerDialog dpd = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setDataTarefa(Constantes.DATE_FORMAT.format(new GregorianCalendar(year, month, dayOfMonth).getTime()));
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        dpd.show();
    }

    public void onHoraTarefaClicked() {
        Calendar c = Calendar.getInstance();

        try {
            c.setTime(Constantes.TIME_FORMAT.parse(horaTarefa));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TimePickerDialog tpd = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                setHoraTarefa(Constantes.TIME_FORMAT.format(new GregorianCalendar(1900, 1, 1, hour, minute).getTime()));
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);

        tpd.show();
    }

    public void onContinuarClicked() {
        if (isInputDataValid()) {
            try {
                tarefa.setDataLimite((Constantes.DATE_TIME_FORMAT.parse(String.format("%s %s", dataTarefa, horaTarefa))));

                goToNextFragment();
            } catch (ParseException e) {
                setToastMessage("O formato da data ou hora é inválido.");
                e.printStackTrace();
            }
        }
    }

    private boolean isInputDataValid() {
        if(TextUtils.isEmpty(tarefa.getDescricao())){
            setToastMessage("A descrição da tarefa não pode ser vazia.");
            return false;
        }
        else if(TextUtils.isEmpty(dataTarefa)) {
            setToastMessage("Você deve informar a data da tarefa.");
            return false;
        }
        else if(TextUtils.isEmpty(horaTarefa)) {
            setToastMessage("Você deve informar o horário da tarefa.");
            return false;
        }

        return true;
    }
}
