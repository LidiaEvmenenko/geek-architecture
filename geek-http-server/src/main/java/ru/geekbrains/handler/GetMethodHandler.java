package ru.geekbrains.handler;

import ru.geekbrains.service.ResponseSerializer;
import ru.geekbrains.config.Config;
import ru.geekbrains.domain.HttpRequest;
import ru.geekbrains.domain.HttpResponse;
import ru.geekbrains.domain.ResponceCode;
import ru.geekbrains.service.SocketService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Handler(order = 1)
public class GetMethodHandler extends MethodHandler{
    public GetMethodHandler(String method, MethodHandler next, SocketService socketService,
                            ResponseSerializer responseSerializer, Config config) {
        super(method, next, socketService, responseSerializer,config);
    }

    @Override
    protected HttpResponse handleInternal(HttpRequest request) {
        Path path = Paths.get(config.getWWWHome(), request.getPath());

        if (!Files.exists(path)) {

            return HttpResponse.createBuilder()
                    .withProtocol(request.getProtocol())
                    .withStatusCode(ResponceCode.NOT_FOUND)
                    .withContent(config.getContent())
                    .withBody("<h1>Файл не найден</h1>")
                    .build();

        } else {
            try {

                StringBuilder sb = new StringBuilder();
                Files.readAllLines(path).forEach(sb::append);

                return HttpResponse.createBuilder()
                        .withProtocol(request.getProtocol())
                        .withStatusCode(ResponceCode.OK)
                        .withContent(config.getContent())
                        .withBody(sb.toString())
                        .build();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
