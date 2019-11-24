package br.com.ifsp.aluno.allex.taskly.events;

import javax.annotation.Nullable;

public interface OnAsyncTaskFinishListener {

    void onAsyncTaskFinished(Boolean success, @Nullable Exception error);

}
