package ru.geekbrains;

import ru.geekbrains.domain.HttpRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestPars implements RequestParser{
    private Map<String, String> headers = new HashMap<>();

    @Override
    public HttpRequest parse(List<String> request) {

        String[] parts = request.get(0).split(" ");

        for (int i = 1; i < request.size(); i++) {
            String[] r = request.get(i).split(" ", 2);
            if (r.length > 1) { this.headers.put(r[0], r[1]);
            }else { this.headers.put(r[0], ""); }
        }
        HttpRequest httpRequest = HttpRequest.createBuilder()
                .withMethod(parts[0])
                .withPath(parts[1])
                .withHeaders(this.headers)
                .withProtocol(parts[2])
                .build();
        return httpRequest;
    }
}
















