package ru.geekbrains.system_patterns.orm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnitOfWork {
    private final List<User> newUsers = new ArrayList<>();
    private final List<User> updateUsers = new ArrayList<>();
    private final List<User> deleteUsers = new ArrayList<>();
    private final Connection conn;
    private PreparedStatement ps;

    public UnitOfWork(Connection conn) {
        this.conn = conn;
    }


    public void registerNew(User user){
        this.newUsers.add(user);
    }
    public void registerUpdate(User user){
        this.updateUsers.add(user);
    }
    public void registerDelete(User user){
        this.deleteUsers.add(user);
    }
    public void commit(){
        insert();
        update();
        delete();
    }

    private void delete() {
        try {
            for (User u : deleteUsers) {
                ps = conn.prepareStatement("delete from users where id= ?;");
                ps.setLong(1, u.getId());
                ps.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void update() {
        try {
            for (User u : updateUsers) {
                ps = conn.prepareStatement("update users set login = ? , password = ? where id = ?;");
                ps.setString(1, u.getLogin());
                ps.setString(2, u.getPassword());
                ps.setLong(3, u.getId());
                ps.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void insert() {
        try {
            for (User u : newUsers) {
                ps = conn.prepareStatement("insert into users (login,password) values(?,?);");
                ps.setString(1, u.getLogin());
                ps.setString(2, u.getPassword());
                ps.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public List<User> getUpdateUsers() {
        return updateUsers;
    }

    public List<User> getDeleteUsers() {
        return deleteUsers;
    }
}
