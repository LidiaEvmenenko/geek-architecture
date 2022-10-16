package ru.geekbrains.domain;

public class HttpResponse {

    private ResponceCode statusCode;
    private String content;
    private String body;
    private String protocol;


    private HttpResponse() {
    }

    public ResponceCode getStatusCode() {
        return statusCode;
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
        public HttpResponseBuilder withStatusCode(ResponceCode responceCode){
            this.response.statusCode = responceCode;
            return this;
        }

        public HttpResponse build(){
            return this.response;
        }
    }
}
