package br.com.ifsp.aluno.allex.taskly.viewmodels;

public class Event<T> {

    private boolean hasBeenHandled = false;
    private T content;

    public Event(T content) {
        this.content = content;
    }

    public T getContentIfNotHandledOrReturnNull() {
        if (hasBeenHandled) {
            return null;
        } else {
            hasBeenHandled = true;
            return content;
        }
    }

    public boolean hasBeenHandled() {
        return hasBeenHandled;
    }
}
