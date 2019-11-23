package br.com.ifsp.aluno.allex.taskly.model;

import java.io.Serializable;
import java.util.Date;

import br.com.ifsp.aluno.allex.taskly.enums.EStatusTarefa;

public class Tarefa implements Serializable {

    private Long id;
    private String descricao;
    private Date data;
    private boolean sincronizada;
    private EStatusTarefa status;
    private String googleAccount;
    private String googleCalendarId;
    private String googleCalendarTaskId;

    public Tarefa() {
        this.id = null;
        this.descricao = null;
        this.data = new Date();
        this.sincronizada = false;
        this.status = EStatusTarefa.PENDENTE;
        this.googleAccount = null;
        this.googleCalendarId = null;
        this.googleCalendarTaskId = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isSincronizada() {
        return sincronizada;
    }

    public void setSincronizada(boolean sincronizada) {
        this.sincronizada = sincronizada;
    }

    public EStatusTarefa getStatus() {
        return status;
    }

    public void setStatus(EStatusTarefa status) {
        this.status = status;
    }

    public String getGoogleAccount() {
        return googleAccount;
    }

    public void setGoogleAccount(String googleAccount) {
        this.googleAccount = googleAccount;
    }

    public String getGoogleCalendarId() {
        return googleCalendarId;
    }

    public void setGoogleCalendarId(String googleCalendarId) {
        this.googleCalendarId = googleCalendarId;
    }

    public String getGoogleCalendarTaskId() {
        return googleCalendarTaskId;
    }

    public void setGoogleCalendarTaskId(String googleCalendarTaskId) {
        this.googleCalendarTaskId = googleCalendarTaskId;
    }
}
