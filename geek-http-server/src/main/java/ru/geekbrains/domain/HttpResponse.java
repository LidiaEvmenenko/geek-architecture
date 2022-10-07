package ru.geekbrains.domain;

public class HttpResponse {
    private int statusCode;
    private String message;
    private String content;
    private String body;
    private String protocol;

    private HttpResponse() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getBody() {
        return body;
    }
    public String getContent() {
        return content;
    }

    public String getProtocol() {
        return protocol;
    }
    public static HttpResponseBuilder createBuilder(){
        return new HttpResponseBuilder();
    }

    public static class HttpResponseBuilder{
        private final HttpResponse response;

        private HttpResponseBuilder() {
            this.response = new HttpResponse();
        }

        public HttpResponseBuilder withStatusCode(int statusCode){
            this.response.statusCode = statusCode;
            return this;
        }
        public HttpResponseBuilder withMessage(String message){
            this.response.message = message;
            return this;
        }
        public HttpResponseBuilder withContent(String content){
            this.response.content = content;
            return this;
        }
        public HttpResponseBuilder withProtocol(String protocol){
            this.response.protocol = protocol;
            return this;
        }
        public HttpResponseBuilder withBody(String body){
            this.response.body = body;
            return this;
        }

        public HttpResponse build(){
            return this.response;
        }
    }
}
