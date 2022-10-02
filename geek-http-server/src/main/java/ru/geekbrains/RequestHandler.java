package ru.geekbrains;

import ru.geekbrains.domain.HttpRequest;
import ru.geekbrains.domain.HttpResponse;
import ru.geekbrains.logger.ConsoleLogger;
import ru.geekbrains.logger.Logger;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class RequestHandler implements Runnable{
    private static final String WWW = "C:\\Java\\geek-architecture\\www";

    private static final Logger logger = new ConsoleLogger();

    private final SocketService socketService;

    public RequestHandler(SocketService socketService) {
        this.socketService = socketService;
    }

    @Override
    public void run() {

        List<String> request = socketService.readRequest();

        RequestPars p = new RequestPars();
        HttpRequest httpRequest = p.parse(request);

        Path path = Paths.get(WWW, httpRequest.getPath());
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setProtocol(httpRequest.getProtocol());
        httpResponse.setContent("Content-Type: text/html; charset=utf-8");
        ResponceSerialize responceSerialize = new ResponceSerialize();
        if (!Files.exists(path)) {
            httpResponse.setStatusCode(404);
            httpResponse.setMessage("NOT_FOUND");
            httpResponse.setBody("<h1>Файл не найден!</h1>");
            socketService.writeResponse(responceSerialize.serialize(httpResponse));
            return;
        }

        try {
            httpResponse.setStatusCode(200);
            httpResponse.setMessage("OK");
            List<String> list = Files.readAllLines(path);
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                s.append(list.get(i)).append("\n");
            }
            httpResponse.setBody(String.valueOf(s));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        socketService.writeResponse(responceSerialize.serialize(httpResponse));
        logger.info("Client disconnected!");
    }
}
