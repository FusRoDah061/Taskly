package br.com.ifsp.aluno.allex.taskly.tasklyweb.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import br.com.ifsp.aluno.allex.taskly.Constantes;

public class TarefaDTO {

    private Long id;
    private Long idUsuario;
    private String descricao;
    private Integer progresso;
    private Date createdAt;
    private String account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getProgresso() {
        return progresso;
    }

    public void setProgresso(Integer progresso) {
        this.progresso = progresso;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String toJson() {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("descricao", this.descricao);
            jsonObject.put("data", Constantes.TASKLY_DATE_TIME_FORMAT.format(this.createdAt));

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
