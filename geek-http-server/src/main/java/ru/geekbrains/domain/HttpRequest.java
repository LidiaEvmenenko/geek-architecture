package ru.geekbrains.domain;

import java.util.Map;

public class HttpRequest {
    private String method;

    private String path;

    private Map<String, String> headers;

    private String body;

    private String protocol;

    private HttpRequest() {
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public String getProtocol() {
        return protocol;
    }
    public static HttpRequestBuilder createBuilder(){
        return new HttpRequestBuilder();
    }
    public static class HttpRequestBuilder{
        private final HttpRequest request;

        private HttpRequestBuilder() {
            this.request = new HttpRequest();
        }
        public HttpRequestBuilder withHeaders(Map<String, String> headers){
            this.request.headers = headers;
            return this;
        }
        public HttpRequestBuilder withMethod(String method){
            this.request.method = method;
            return this;
        }
        public HttpRequestBuilder withPath(String path){
            this.request.path = path;
            return this;
        }
        public HttpRequestBuilder withProtocol(String protocol){
            this.request.protocol = protocol;
            return this;
        }
        public HttpRequestBuilder withBody(String body){
            this.request.body = body;
            return this;
        }

        public HttpRequest build(){
            return this.request;
        }
    }
}
