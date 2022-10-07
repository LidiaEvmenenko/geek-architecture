package ru.geekbrains;

import ru.geekbrains.logger.ConsoleLogger;
import ru.geekbrains.logger.Logger;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SocketService implements Closeable {
    private static final Logger logger = new ConsoleLogger();

    private final Socket socket;

    private SocketService(Socket socket) {
        this.socket = socket;
    }

    public static SocketService createSocketService(Socket socket){
        return new SocketService(socket);
    }

    public List<String> readRequest() {
        try {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream(), StandardCharsets.UTF_8));

            while (!input.ready());

            List<String> request = new ArrayList<>();
            while (input.ready()) {
                String line = input.readLine();
                logger.info(line);
                request.add(line);
            }
            return request;
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public void writeResponse(String headers) {
        try {
            PrintWriter output = new PrintWriter(socket.getOutputStream());
            output.print(headers);
            output.flush();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }


    @Override
    public void close() throws IOException {
        if (!socket.isClosed()) {
            socket.close();
        }
    }

}
