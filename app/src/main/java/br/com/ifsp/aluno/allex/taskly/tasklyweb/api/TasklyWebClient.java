package br.com.ifsp.aluno.allex.taskly.tasklyweb.api;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.enums.EStatusTarefa;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.httpclient.HttpClient;
import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.httpclient.HttpException;
import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.httpclient.HttpResponse;
import br.com.ifsp.aluno.allex.taskly.ui.InputDialog;

public class TasklyWebClient {

    public List<TarefaDTO> getTarefas(String account) {
        HttpClient client = new HttpClient.Builder()
                .setUrl(Constantes.URL_ENDPOINT_TASKLY_TAREFAS + "/" + account)
                .setMethod("GET")
                .build();

        HttpResponse response = client.getResponse();

        return parseTarefas(response);
    }

    public TarefaDTO getTarefa(Long id) {
        HttpClient client = new HttpClient.Builder()
                .setUrl(Constantes.URL_ENDPOINT_TASKLY_TAREFA + "/" + id)
                .setMethod("GET")
                .build();

        HttpResponse response = client.getResponse();

        return parseTarefa(response);
    }

    public TarefaDTO criaTarefa(TarefaDTO tarefa) {
        ContentValues headers = new ContentValues();
        headers.put("Content-type", "application/json");

        HttpClient client = new HttpClient.Builder()
                .setUrl(Constantes.URL_ENDPOINT_TASKLY_TAREFAS + "/" + tarefa.getAccount())
                .setMethod("POST")
                .setHeaders(headers)
                .setBody(tarefa.toJson())
                .build();

        HttpResponse response = client.getResponse();

        if(response.getResponseCode() != 200) {
            throw  new HttpException(response.getResponseCode(), response.getResponseMessage());
        }

        return parseTarefa(response);
    }

    public TarefaDTO atualizaTarefa(TarefaDTO tarefa) {
        ContentValues headers = new ContentValues();
        headers.put("Content-type", "application/json");

        HttpClient client = new HttpClient.Builder()
                .setUrl(Constantes.URL_ENDPOINT_TASKLY_TAREFAS + "/" + tarefa.getId())
                .setMethod("PUT")
                .setHeaders(headers)
                .setBody(tarefa.toJson())
                .build();

        HttpResponse response = client.getResponse();

        if(response.getResponseCode() != 200) {
            throw  new HttpException(response.getResponseCode(), response.getResponseMessage());
        }

        return parseTarefa(response);
    }

    public void deleteTarefa(Long id) {
        HttpClient client = new HttpClient.Builder()
                .setUrl(Constantes.URL_ENDPOINT_TASKLY_TAREFAS + "/" + id)
                .setMethod("DELETE")
                .build();

        HttpResponse response = client.getResponse();

        if(response.getResponseCode() != 200) {
            throw  new HttpException(response.getResponseCode(), response.getResponseMessage());
        }
    }

    public static TarefaDTO mapTarefaToTarefaDTO(Tarefa tarefa) {
        TarefaDTO dto = new TarefaDTO();
        dto.setDescricao(tarefa.getDescricao());
        dto.setProgresso(tarefa.getStatus() == EStatusTarefa.CONCLUIDA ? 100 : 0);
        dto.setCreatedAt(tarefa.getDataCriacao());
        dto.setId(tarefa.getTasklyTaskId());
        dto.setAccount(tarefa.getTasklyAccount());

        return dto;
    }

    public static Tarefa mapTarefaDTOToTarefa(TarefaDTO tarefaDto) {
        Tarefa tarefa = new Tarefa();

        tarefa.setStatus(tarefaDto.getProgresso() == 100 ? EStatusTarefa.CONCLUIDA : EStatusTarefa.PENDENTE);
        tarefa.setSincronizada(true);
        tarefa.setDescricao(tarefaDto.getDescricao());
        tarefa.setTasklyAccount(tarefaDto.getAccount());
        tarefa.setTasklyTaskId(tarefaDto.getId());
        tarefa.setDataCriacao(tarefaDto.getCreatedAt());
        tarefa.setDataLimite(tarefaDto.getCreatedAt());

        return tarefa;
    }

    public static void askTasklyAccount(final Context context) {
        final InputDialog inputDialog = new InputDialog(context);
        inputDialog
                .setTitle(context.getResources().getString(R.string.str_input_dialog_add_conta_title))
                .setPlaceholder(context.getResources().getString(R.string.str_input_dialog_add_conta_placeholder))
                .setPositiveButton(context.getResources().getString(R.string.str_input_dialog_add_conta_posbutton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = inputDialog.getValue();

                        if(!TextUtils.isEmpty(email)) {
                            SharedPreferences prefs = context.getSharedPreferences(Constantes.PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString(Constantes.PREF_CONTA_PADRAO, email);
                            editor.commit();

                            Toast.makeText(context, email + context.getResources().getString(R.string.str_default_account_set), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, context.getResources().getString(R.string.str_input_dialog_add_conta_noaccount), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(context.getResources().getString(R.string.str_input_dialog_add_conta_negbutton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, context.getResources().getString(R.string.str_input_dialog_add_conta_noaccount), Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private List<TarefaDTO> parseTarefas(HttpResponse response) {
        try {
            List<TarefaDTO> tarefas = new ArrayList<>();
            JSONArray array = parseCollectionResponse(response);

            if(array != null) {
                for (int i=0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    TarefaDTO tarefaDTO = new TarefaDTO();

                    tarefaDTO.setId(object.getLong("id"));
                    tarefaDTO.setIdUsuario(object.getLong("id_usuario"));
                    tarefaDTO.setDescricao(object.getString("descricao"));
                    tarefaDTO.setProgresso(object.getInt("progresso"));
                    tarefaDTO.setCreatedAt(Constantes.DATE_TIME_FORMAT.parse(object.getString("created_at")));

                    tarefas.add(tarefaDTO);
                }
            }

            return tarefas;

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private TarefaDTO parseTarefa(HttpResponse response) {
        try {
            JSONObject object = parseResponse(response);
            TarefaDTO tarefaDTO = new TarefaDTO();

            tarefaDTO.setId(object.getLong("id"));
            tarefaDTO.setIdUsuario(object.getLong("id_usuario"));
            tarefaDTO.setDescricao(object.getString("descricao"));
            tarefaDTO.setProgresso(object.getInt("progresso"));
            tarefaDTO.setCreatedAt(Constantes.DATE_TIME_FORMAT.parse(object.getString("created_at")));

            return tarefaDTO;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private JSONObject parseResponse(HttpResponse response) throws JSONException, HttpException {
        if(!String.valueOf(response.getResponseCode()).startsWith("2")) {
            throw  new HttpException(response.getResponseCode(), response.getResponseMessage());
        }

        if(response.getContent() != null) {
            return new JSONObject(response.getContent());
        }

        return null;
    }

    private JSONArray parseCollectionResponse(HttpResponse response) throws JSONException, HttpException {
        if(!String.valueOf(response.getResponseCode()).startsWith("2")) {
            throw  new HttpException(response.getResponseCode(), response.getResponseMessage());
        }

        if(response.getContent() != null) {
            return new JSONArray(response.getContent());
        }

        return null;
    }

}
