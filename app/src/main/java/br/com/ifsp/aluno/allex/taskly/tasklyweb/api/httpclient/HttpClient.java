package br.com.ifsp.aluno.allex.taskly.tasklyweb.api.httpclient;

import android.content.ContentValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.ifsp.aluno.allex.taskly.Constantes;

public class HttpClient {

    private String url;
    private String method;
    private ContentValues headers;
    private ContentValues query;
    private String body;

    public HttpClient(String url, String method, ContentValues headers, ContentValues query, String body) {
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.query = query;
        this.body = body;

        if(url == null || "".equals(url))
            throw new NullPointerException("URL é obrigatória");

        if(method == null || "".equals(method))
            throw new NullPointerException("Método é obrigatório");
    }

    public HttpResponse getResponse() {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        HttpResponse response = new HttpResponse();

        try {
            URL urlObj = new URL(url + buildQueryString());

            urlConnection = (HttpURLConnection) urlObj.openConnection();
            urlConnection.setRequestMethod(method);

            if(headers != null)
                setHeaderFields(urlConnection);

            urlConnection.setConnectTimeout(Constantes.HTTP_REQUEST_TIMEOUT);
            urlConnection.setReadTimeout(Constantes.HTTP_REQUEST_TIMEOUT);

            urlConnection.connect();

            if(body != null)
                writeContent(urlConnection.getOutputStream());

            response.setResponseCode(urlConnection.getResponseCode());
            response.setResponseMessage(urlConnection.getResponseMessage());

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String linha;
            StringBuilder buffer = new StringBuilder();

            while ((linha = reader.readLine()) != null) {
                buffer.append(linha);
            }

            response.setContent(buffer.toString());
            return response;
        }
        catch (Exception e) {
            e.printStackTrace();

            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return null;
    }

    private void writeContent(OutputStream outputStream) {
        OutputStreamWriter osw = null;

        try {
            osw = new OutputStreamWriter(outputStream, "UTF-8");

            osw.write(body);
            osw.flush();
            osw.close();
            outputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setHeaderFields(HttpURLConnection urlConnection) {
        for (String key : headers.keySet()) {
            urlConnection.setRequestProperty(key, headers.getAsString(key));
        }
    }

    private String buildQueryString() {
        if(query == null) return "";

        String queryString = "?";

        for (String key : query.keySet()) {
            queryString += String.format("%s=%s&", key, query.getAsString(key));
        }

        return queryString;
    }

    public static class Builder {

        private String url;
        private String method;
        private ContentValues headers;
        private ContentValues query;
        private String body;

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setHeaders(ContentValues headers) {
            this.headers = headers;
            return this;
        }

        public Builder setQuery(ContentValues query) {
            this.query = query;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public HttpClient build(){
            return new HttpClient(url, method, headers, query, body);
        }
    }
}
