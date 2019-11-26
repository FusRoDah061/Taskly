package br.com.ifsp.aluno.allex.taskly.tasklyweb.api.httpclient;

public class HttpException extends RuntimeException {

    public HttpException(int responseCode, String responseMessage) {
        super(String.valueOf(responseCode) + " " + responseMessage);
    }
}
