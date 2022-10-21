package ru.geekbrains.handler;

import ru.geekbrains.service.ResponseSerializer;
import ru.geekbrains.config.Config;
import ru.geekbrains.domain.HttpRequest;
import ru.geekbrains.domain.HttpResponse;
import ru.geekbrains.domain.ResponceCode;
import ru.geekbrains.service.SocketService;

public abstract class MethodHandler {
    private final String method;
    private final MethodHandler next;
    protected SocketService socketService;
    protected final ResponseSerializer responseSerializer;
    protected final Config config;

    public MethodHandler(String method, MethodHandler next, SocketService socketService,
                         ResponseSerializer responseSerializer, Config config) {
        this.method = method;
        this.next = next;
        this.socketService = socketService;
        this.responseSerializer = responseSerializer;
        this.config = config;
    }

    public void heandle(HttpRequest request) {
        HttpResponse httpResponse;
        if (method.equals(request.getMethod())) {
            httpResponse = handleInternal(request);
        } else if (next != null) {
            next.heandle(request);
            return;
        } else {
            httpResponse = HttpResponse.createBuilder()
                    .withProtocol(request.getProtocol())
                    .withStatusCode(ResponceCode.METOD_NOT_ALLOWED)
                    .withContent("Content-Type: text/html; charset=utf-8")
                    .withBody("<h1>Метод не поддерживается!</h1>")
                    .build();
        }
        socketService.writeResponse(responseSerializer.serialize(httpResponse));
    }

    protected abstract HttpResponse handleInternal(HttpRequest request);
    public MethodHandler getNext() {
        return next;
    }
}
