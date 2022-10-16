package ru.geekbrains;

import ru.geekbrains.config.Config;
import ru.geekbrains.config.ConfigFactory;
import ru.geekbrains.handler.MethodHandlerFactory;
import ru.geekbrains.handler.RequestHandler;
import ru.geekbrains.logger.ConsoleLogger;
import ru.geekbrains.logger.Logger;
import ru.geekbrains.service.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private static final Logger logger = new ConsoleLogger();

    public static void main(String[] args) {
        Config config = ConfigFactory.create(args);
        try (ServerSocket serverSocket = new ServerSocket(config.getPort())) {
            System.out.printf("Server started at port %d!%n ", config.getPort());

            while (true) {
                Socket socket = serverSocket.accept();
                logger.info("New client connected!");

                SocketService socketService = SocketServiceFactory.createSocketService(socket);
                ResponseSerializer responceSerializer = ResponceSerializerFactory.createResponceSerializer();
                new Thread(RequestHandler.createRequestHandler(
                         socketService,

                        RequestParserFactory.createRequestParser(),

                        MethodHandlerFactory.create(socketService,responceSerializer,config))).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}














