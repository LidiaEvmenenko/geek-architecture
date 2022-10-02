package ru.geekbrains.domain;

public class HttpResponse {
    private int statusCode;
    private String message;
    private String content;
    private String body;
    private String protocol;

    public HttpResponse(int statusCode, String message, String content, String body,String protocol) {
        this.statusCode = statusCode;
        this.message = message;
        this.content = content;
        this.body = body;
        this.protocol = protocol;
    }

    public HttpResponse() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
