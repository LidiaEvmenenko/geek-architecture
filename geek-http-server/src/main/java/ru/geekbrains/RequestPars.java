package ru.geekbrains;

import ru.geekbrains.domain.HttpRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestPars implements RequestParser{
    private Map<String, String> headers = new HashMap<>();

    @Override
    public HttpRequest parse(List<String> request) {
        HttpRequest httpRequest = new HttpRequest();
        String[] parts = request.get(0).split(" ");
        httpRequest.setMethod(parts[0]);
        httpRequest.setPath(parts[1]);
        for (int i = 1; i < request.size(); i++) {
            String[] r = request.get(i).split(" ", 2);
            if (r.length > 1) { this.headers.put(r[0], r[1]);
            }else { this.headers.put(r[0], ""); }
        }
        httpRequest.setHeaders(this.headers);
        httpRequest.setProtocol(parts[2]);
        return httpRequest;
    }
}
