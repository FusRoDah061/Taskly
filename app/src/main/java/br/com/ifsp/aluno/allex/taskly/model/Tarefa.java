package br.com.ifsp.aluno.allex.taskly.model;

import java.io.Serializable;
import java.util.Date;

import br.com.ifsp.aluno.allex.taskly.enums.EStatusTarefa;

public class Tarefa implements Serializable {

    private Long id;
    private String descricao;
    private Date dia;
    private boolean sincronizada;
    private EStatusTarefa status;

    public Tarefa(String descricao, Date dia, boolean sincronizada) {
        this.descricao = descricao;
        this.dia = dia;
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

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
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
