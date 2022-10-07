package ru.geekbrains;

import ru.geekbrains.config.Config;
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

    private static final Logger logger = new ConsoleLogger();
    private final Config config;
    private final SocketService socketService;

    private RequestHandler(SocketService socketService, Config config) {
        this.config = config;
        this.socketService = socketService;
    }
    public static RequestHandler createRequestHandler(SocketService socketService, Config config){
        return new RequestHandler(socketService, config);
    }

    @Override
    public void run() {

        List<String> request = socketService.readRequest();

        RequestPars p = new RequestPars();
        HttpRequest httpRequest = p.parse(request);

        Path path = Paths.get(config.getWWWHome(), httpRequest.getPath());
        HttpResponse httpResponse;

        ResponceSerialize responceSerialize = new ResponceSerialize();
        if (!Files.exists(path)) {
            httpResponse = HttpResponse.createBuilder()
                    .withProtocol(httpRequest.getProtocol())
                    .withContent(config.getContent())
                    .withStatusCode(404)
                    .withMessage("NOT_FOUND")
                    .withBody("<h1>Файл не найден!</h1>")
                    .build();
        } else {
            try {
                List<String> list = Files.readAllLines(path);
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    s.append(list.get(i)).append("\n");
                }
                httpResponse = HttpResponse.createBuilder()
                        .withProtocol(httpRequest.getProtocol())
                        .withContent(config.getContent())
                        .withStatusCode(200)
                        .withMessage("OK")
                        .withBody(String.valueOf(s))
                        .build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
            socketService.writeResponse(responceSerialize.serialize(httpResponse));
            logger.info("Client disconnected!");

    }
}
