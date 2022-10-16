package ru.geekbrains.service;

public class ResponceSerializerFactory {
    public static ResponseSerializer createResponceSerializer(){
        return new ResponceSerializerImpl();
    }
}
