package ru.geekbrains.system_patterns.orm;

import java.sql.*;

public class DBProvider {
    private Connection connection;
    private PreparedStatement ps;

    public DBProvider() {
        connect();
        createTable();

    }

    public Connection getConnection() {
        return connection;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:dbjava.db");
            connection.setAutoCommit(true); //автоматически закрывается транзакция
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public void createTable() {
        try {
            ps = connection.prepareStatement("create table if not exists users (" +
                    "id integer primary key autoincrement," +
                    "login text not null," +
                    "password text not null)");
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void selectTable(){
        try {
            ps = connection.prepareStatement("select * from users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.printf("%d %s %s \n", rs.getLong(1),
                        rs.getString(2), rs.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void dropTable() {
        try {
            ps = connection.prepareStatement("drop table users");
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
