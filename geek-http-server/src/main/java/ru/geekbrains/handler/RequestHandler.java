package ru.geekbrains.handler;

import ru.geekbrains.domain.HttpRequest;
import ru.geekbrains.logger.ConsoleLogger;
import ru.geekbrains.logger.Logger;
import ru.geekbrains.service.RequestParser;
import ru.geekbrains.service.SocketService;

import java.io.IOException;
import java.util.List;

public class RequestHandler implements Runnable{

    private static final Logger logger = new ConsoleLogger();
    private final SocketService socketService;
    private final RequestParser requestParser;
    private final MethodHandler methodHandler;

    private RequestHandler(SocketService socketService, RequestParser requestParser,

                           MethodHandler methodHandler) {
        this.requestParser = requestParser;
        this.methodHandler = methodHandler;
        this.socketService = socketService;
    }
    public static RequestHandler createRequestHandler(SocketService socketService,RequestParser requestParser,

                                                      MethodHandler methodHandler){
        return new RequestHandler(socketService, requestParser, methodHandler);
    }

    @Override
    public void run() {

        List<String> request = socketService.readRequest();

        HttpRequest httpRequest = requestParser.parse(request);
        methodHandler.heandle(httpRequest);
        try {
            socketService.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
            logger.info("Client disconnected!");

    }
}
