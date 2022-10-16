package ru.geekbrains.service;

import ru.geekbrains.domain.HttpResponse;

public interface ResponseSerializer {
    String serialize(HttpResponse httpResponse);
}
