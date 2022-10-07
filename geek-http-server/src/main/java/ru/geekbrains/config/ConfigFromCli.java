package ru.geekbrains.config;

public class ConfigFromCli implements Config{
    private final String wwwHome;
    private final int port;
    private final String content;

    public ConfigFromCli(String[] args) {
        if (args.length < 2) {
            throw new IllegalStateException("No parameters specified");
        }
        this.wwwHome = args[0];
        this.port = Integer.parseInt(args[1]);
        this.content = args[2];
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
