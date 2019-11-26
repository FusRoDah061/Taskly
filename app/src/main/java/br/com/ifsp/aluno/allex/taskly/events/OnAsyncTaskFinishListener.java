package br.com.ifsp.aluno.allex.taskly.events;

import javax.annotation.Nullable;

public interface OnAsyncTaskFinishListener<T> {

    void onAsyncTaskFinished(T result, @Nullable Exception error);

}
