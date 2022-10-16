package ru.geekbrains.domain;

public enum ResponceCode {
    OK(200,"OK"),
    BAD_REQUEST(400,"BAD REQUEST"),
    NOT_FOUND(404,"NOT FOUND"),
    METOD_NOT_ALLOWED(405, "METOD NOT ALLOWED");
    final int status;
    final String name;

    ResponceCode(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
