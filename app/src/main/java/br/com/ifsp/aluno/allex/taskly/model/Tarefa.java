package br.com.ifsp.aluno.allex.taskly.model;

import java.util.Date;

import br.com.ifsp.aluno.allex.taskly.enums.EStatusTarefa;

public class Tarefa {

    private Long id;
    private String descricao;
    private Date data;
    private boolean sincronizada;
    private EStatusTarefa status;

    public Tarefa() {
        this.descricao = null;
        this.data = new Date();
        this.sincronizada = false;
    }

    public Tarefa(String descricao, Date data, boolean sincronizada) {
        this.descricao = descricao;
        this.data = data;
        this.sincronizada = sincronizada;
    }

    public Long getId() {
        return id;
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
}
