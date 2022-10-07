package ru.geekbrains.config;

import java.io.IOException;
import java.util.Properties;

public class ConfigFromFile implements Config{
    private final String wwwHome;
    private final int port;
    private final String content;

    public ConfigFromFile(String fileName) {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream(fileName));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        this.wwwHome = prop.getProperty("www.home");
        this.port = Integer.parseInt(prop.getProperty("port"));
        this.content = prop.getProperty("content");
    }
    @Override
    public String getWWWHome() {
        return this.wwwHome;
    }

    @Override
    public int getPort() {
        return this.port;
    }
    @Override
    public String getContent() {
        return this.content;
    }
}
