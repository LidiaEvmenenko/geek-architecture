package ru.geekbrains.service;

import ru.geekbrains.domain.HttpRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RequestParserImpl implements RequestParser {


    @Override
    public HttpRequest parse(List<String> request) {
        Map<String, String> headers = new HashMap<>();
        String[] parts = request.get(0).split(" ");

        for (int i = 1; i < request.size(); i++) {
            String[] r = request.get(i).split(" ", 2);
            if (r.length > 1) { headers.put(r[0], r[1]);
            }else { headers.put(r[0], ""); }
        }
        HttpRequest httpRequest = HttpRequest.createBuilder()
                .withMethod(parts[0])
                .withPath(parts[1])
                .withHeaders(headers)
                .withProtocol(parts[2])
                .build();
        return httpRequest;
    }
}
















