package ru.geekbrains;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class BufWrite {
    private PrintWriter output;

    public BufWrite(Socket socket){
        try {
            output = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PrintWriter getOutput() {
        return output;
    }
}
