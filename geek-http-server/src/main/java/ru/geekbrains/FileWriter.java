package ru.geekbrains;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriter {
    private PrintWriter output;

    public FileWriter(PrintWriter output){
        this.output = output;
    }

    public void writeFile(Path path){
        if (!Files.exists(path)) {
            output.println("HTTP/1.1 404 NOT_FOUND");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<h1>Файл не найден!</h1>");
            output.flush();
            return;
        }

        output.println("HTTP/1.1 200 OK");
        output.println("Content-Type: text/html; charset=utf-8");
        output.println();

        try {
            Files.newBufferedReader(path).transferTo(output);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
