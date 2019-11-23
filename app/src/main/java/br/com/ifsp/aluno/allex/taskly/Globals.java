package br.com.ifsp.aluno.allex.taskly;

public class Globals {

    private static Globals instance;

    private boolean perguntarSincronizar = true;
    private boolean indicaSincronizar = true;

    private Globals() {}

    public static Globals getInstance(){
        if(instance == null)
            instance = new Globals();

        return instance;
    }

    public boolean isPerguntarSincronizar() {
        return perguntarSincronizar;
    }

    public void setPerguntarSincronizar(boolean perguntarSincronizar) {
        this.perguntarSincronizar = perguntarSincronizar;
    }

    public boolean isIndicaSincronizar() {
        return indicaSincronizar;
    }

    public void setIndicaSincronizar(boolean indicaSincronizar) {
        this.indicaSincronizar = indicaSincronizar;
    }
}
