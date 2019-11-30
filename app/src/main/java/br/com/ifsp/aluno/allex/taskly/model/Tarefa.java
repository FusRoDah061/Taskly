package br.com.ifsp.aluno.allex.taskly.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import br.com.ifsp.aluno.allex.taskly.enums.EStatusTarefa;

public class Tarefa implements Serializable {

    private Long id;
    private String descricao;
    private Date dataLimite;
    private Date dataCriacao;
    private Double latitudeCriacao;
    private Double longitudeCriacao;
    private boolean sincronizada;
    private EStatusTarefa status;
    private Long tasklyTaskId;
    private String tasklyAccount;

    public Tarefa() {
        this.id = null;
        this.descricao = null;
        this.dataLimite = new Date();
        this.dataCriacao = new Date();
        this.sincronizada = false;
        this.status = EStatusTarefa.PENDENTE;
        this.tasklyTaskId = null;
        this.tasklyAccount = null;
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

    public Date getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(Date dataLimite) {
        this.dataLimite = dataLimite;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Double getLatitudeCriacao() {
        return latitudeCriacao;
    }

    public void setLatitudeCriacao(Double latitudeCriacao) {
        this.latitudeCriacao = latitudeCriacao;
    }

    public Double getLongitudeCriacao() {
        return longitudeCriacao;
    }

    public void setLongitudeCriacao(Double longitudeCriacao) {
        this.longitudeCriacao = longitudeCriacao;
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

    public Long getTasklyTaskId() {
        return tasklyTaskId;
    }

    public void setTasklyTaskId(Long tasklyTaskId) {
        this.tasklyTaskId = tasklyTaskId;
    }

    public String getTasklyAccount() {
        return tasklyAccount;
    }

    public void setTasklyAccount(String tasklyAccount) {
        this.tasklyAccount = tasklyAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarefa tarefa = (Tarefa) o;
        return Objects.equals(getId(), tarefa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
