package br.com.ifsp.aluno.allex.taskly.enums;

import androidx.annotation.NonNull;

public enum EStatusTarefa {
    CONCLUIDA("CONCLUIDA"),
    PENDENTE("PENDENTE");

    private String status;

    EStatusTarefa(String status) {
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

    @NonNull
    @Override
    public String toString() {
        return status;
    }
}
