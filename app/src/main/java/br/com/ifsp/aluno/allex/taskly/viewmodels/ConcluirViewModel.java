package br.com.ifsp.aluno.allex.taskly.viewmodels;

import android.location.Location;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import javax.annotation.Nullable;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.events.OnAsyncTaskFinishListener;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.notifications.TarefaNotificationReceiver;
import br.com.ifsp.aluno.allex.taskly.persistence.repository.TarefaRepository;
import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.TarefaDTO;
import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.TasklyWebClient;
import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.tasks.AtualizarTarefaAsyncTask;
import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.tasks.CriarTarefaAsyncTask;
import br.com.ifsp.aluno.allex.taskly.views.AsyncActivity;

public class ConcluirViewModel extends BaseViewModel {

    public ConcluirViewModel(Tarefa tarefa, AsyncActivity activity) {
        super(tarefa, activity);
    }

    @Bindable
    public String getDescricaoTarefa() {
        return tarefa.getDescricao();
    }

    @Bindable
    public String getHoraTarefa() {
        return Constantes.TIME_FORMAT.format(tarefa.getDataLimite());
    }

    @Bindable
    public String getDataTarefa() {
        return Constantes.DATE_FORMAT.format(tarefa.getDataLimite());
    }

    @Bindable
    public String getIsSincronizada() {
        if(tarefa.isSincronizada())
            return activity.getResources().getString(R.string.str_tarefa_sincronizada_aviso);
        else
            return "";
    }

    public void onLegalClicked() {
        if(!tarefa.isSincronizada()) {
            obterLocalizacao();
            return;
        }

        final TarefaDTO tarefaDTO = TasklyWebClient.mapTarefaToTarefaDTO(tarefa);

        if(tarefa.getTasklyTaskId() != null) {
            AtualizarTarefaAsyncTask atualizarTarefaAsyncTask = new AtualizarTarefaAsyncTask(activity);

            atualizarTarefaAsyncTask.setOnAsyncTaskFinishListener(new OnAsyncTaskFinishListener<TarefaDTO>() {
                @Override
                public void onAsyncTaskFinished(TarefaDTO result, @Nullable Exception error) {
                    if (result != null) {
                        obterLocalizacao();
                    } else {
                        if (error != null) {
                            error.printStackTrace();
                        }

                        Toast.makeText(activity, activity.getResources().getString(R.string.error_sync_update_tarefa), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            atualizarTarefaAsyncTask.execute(tarefaDTO);
        }
        else {
            CriarTarefaAsyncTask criarTarefaAsyncTask = new CriarTarefaAsyncTask(activity);

            criarTarefaAsyncTask.setOnAsyncTaskFinishListener(new OnAsyncTaskFinishListener<TarefaDTO>() {
                @Override
                public void onAsyncTaskFinished(TarefaDTO result, @Nullable Exception error) {
                    if (result != null) {
                        tarefa.setTasklyTaskId(result.getId());

                        obterLocalizacao();
                    } else {
                        if (error != null) {
                            error.printStackTrace();
                        }

                        Toast.makeText(activity, activity.getResources().getString(R.string.error_sync_send_tarefa), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            criarTarefaAsyncTask.execute(tarefaDTO);
        }

    }

    private void obterLocalizacao() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

        fusedLocationClient.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    tarefa.setLatitudeCriacao(location.getLatitude());
                    tarefa.setLongitudeCriacao(location.getLongitude());
                }

                finalizarCriacaoTarefa();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(activity, activity.getResources().getString(R.string.error_couldnt_get_location), Toast.LENGTH_SHORT).show();
                finalizarCriacaoTarefa();
            }
        })
        .addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(activity, activity.getResources().getString(R.string.error_couldnt_get_location), Toast.LENGTH_SHORT).show();
                finalizarCriacaoTarefa();
            }
        });
    }

    private void finalizarCriacaoTarefa() {
        new TarefaRepository(activity).save(tarefa);

        TarefaNotificationReceiver tarefaNotificationReceiver = new TarefaNotificationReceiver();
        tarefaNotificationReceiver.scheduleNotification(activity, tarefa);

        goToNextFragment();
    }
}
