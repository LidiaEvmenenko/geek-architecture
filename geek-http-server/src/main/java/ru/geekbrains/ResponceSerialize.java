package ru.geekbrains;

import ru.geekbrains.domain.HttpResponse;

public class ResponceSerialize implements ResponseSerializer{
    @Override
    public String serialize(HttpResponse httpResponse) {
        StringBuilder s = new StringBuilder();
        s.append(httpResponse.getProtocol()).append(" ")
                .append(httpResponse.getStatusCode()).append(" ")
                .append(httpResponse.getMessage()).append("\n")
                .append(httpResponse.getContent()).append("\n").append("\n")
                .append(httpResponse.getBody());
        return String.valueOf(s);
    }
}
