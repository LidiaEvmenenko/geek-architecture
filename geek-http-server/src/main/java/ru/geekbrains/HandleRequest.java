package ru.geekbrains;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HandleRequest {
    private Socket socket;
    private FileWriter fileWriter;
    private static String WWW = "C:\\Java\\geek-architecture\\www";

    public HandleRequest(Socket socket) throws IOException {
        this.socket = socket;
        BufWrite bufWrite = new BufWrite(socket);
        BufRead bufRead = new BufRead(socket);
        fileWrite(bufRead,bufWrite);
    }

    private void fileWrite(BufRead buf, BufWrite bufWrite){
        try (BufferedReader input = buf.getInput();
             PrintWriter output = bufWrite.getOutput();

        ) {
            fileWriter = new FileWriter(output);
            while (!input.ready()) ;
            String firstLine = input.readLine();
            String[] parts = firstLine.split(" ");

            while (input.ready()) {
                System.out.println(input.readLine());
            }
            Path path = Paths.get(WWW, parts[1]);
            fileWriter.writeFile(path);

            System.out.println("Client disconnected!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
