package ru.geekbrains.service;

import ru.geekbrains.domain.HttpResponse;
import ru.geekbrains.service.ResponseSerializer;

class ResponceSerializerImpl implements ResponseSerializer {
    @Override
    public String serialize(HttpResponse httpResponse) {
        StringBuilder s = new StringBuilder();
        s.append(httpResponse.getProtocol()).append(" ")
                .append(httpResponse.getStatusCode()).append(" ")
                .append(httpResponse.getContent()).append("\n").append("\n")
                .append(httpResponse.getBody());
        return String.valueOf(s);
    }
}
