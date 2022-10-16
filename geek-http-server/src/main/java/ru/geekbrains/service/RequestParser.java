package ru.geekbrains.service;

import ru.geekbrains.domain.HttpRequest;

import java.util.List;

public interface RequestParser {
    HttpRequest parse(List<String> rawRequest);
}
