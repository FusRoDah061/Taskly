package br.com.ifsp.aluno.allex.taskly.tasklyweb.api;

import android.content.ContentValues;

import com.google.api.client.http.HttpResponseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.httpclient.HttpClient;
import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.httpclient.HttpException;
import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.httpclient.HttpResponse;

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

    public void criaTarefa(Long id, TarefaDTO tarefa) {
        ContentValues headers = new ContentValues();
        headers.put("Content-type", "application/json");

        HttpClient client = new HttpClient.Builder()
                .setUrl(Constantes.URL_ENDPOINT_TASKLY_TAREFAS + "/" + id)
                .setMethod("POST")
                .setHeaders(headers)
                .build();

        HttpResponse response = client.getResponse();

        if(response.getResponseCode() != 200) {
            throw  new HttpException(response.getResponseCode(), response.getResponseMessage());
        }
    }

    public void atualizaTarefa(Long id, TarefaDTO tarefa) {
        ContentValues headers = new ContentValues();
        headers.put("Content-type", "application/json");

        HttpClient client = new HttpClient.Builder()
                .setUrl(Constantes.URL_ENDPOINT_TASKLY_TAREFAS + "/" + id)
                .setMethod("PUT")
                .setHeaders(headers)
                .build();

        HttpResponse response = client.getResponse();

        if(response.getResponseCode() != 200) {
            throw  new HttpException(response.getResponseCode(), response.getResponseMessage());
        }
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
