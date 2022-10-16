package ru.geekbrains.system_patterns.orm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserMapper {

    private PreparedStatement selectUser;
    private final Map<Long, User> identityMap = new HashMap<>();

    public UserMapper(Connection conn) {
        try {
            this.selectUser = conn.prepareStatement("select id,login,password from users where id= ?;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Optional<User> findId(long id) {
        User user = identityMap.get(id);
        if (user != null){
            return Optional.of(user);
        }
        try {
            selectUser.setLong(1, id);
            ResultSet rs = selectUser.executeQuery();
            if (rs.next()) {
                user = new User(rs.getLong(1), rs.getString(2), rs.getString(3));
                identityMap.put(id,user);
                return Optional.of(user);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return Optional.empty();
    }

}
