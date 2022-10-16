package ru.geekbrains.handler;

import ru.geekbrains.service.ResponseSerializer;
import ru.geekbrains.config.Config;
import ru.geekbrains.domain.HttpRequest;
import ru.geekbrains.domain.HttpResponse;
import ru.geekbrains.domain.ResponceCode;
import ru.geekbrains.service.SocketService;

@Handler(order = 2)
public class PostMethodHandler extends MethodHandler{
    public PostMethodHandler(String method, MethodHandler next, SocketService socketService,
                             ResponseSerializer responseSerializer, Config config) {
        super(method, next, socketService, responseSerializer, config);
    }

    @Override
    protected HttpResponse handleInternal(HttpRequest request) {
        return HttpResponse.createBuilder()
                .withProtocol(request.getProtocol())
                .withStatusCode(ResponceCode.OK)
                .withContent(config.getContent())
                .withBody("<h1>POST method</h1>")
                .build();
    }
}
