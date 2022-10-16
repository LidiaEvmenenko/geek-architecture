package ru.geekbrains.system_patterns.orm;

import java.sql.Connection;

public class MainApp {
    public static void main(String[] args) {

        DBProvider db = new DBProvider();
        Connection connection = db.getConnection();
        UserRepository userRepository = new UserRepository(connection);
        User user = new User(1L, "user1", "111");
        userRepository.insert(user);
        user = new User(2L, "user2", "222");
        userRepository.insert(user);
        user = new User(3L, "user3", "333");
        userRepository.insert(user);
        user = new User(4L, "user4", "444");
        userRepository.insert(user);
        user = new User(2L, "user123", "123");
        userRepository.update(user);
        user = new User(1L, "user1", "111");
        userRepository.delete(user);
        user = new User(4L, "user4", "444");
        userRepository.delete(user);
        userRepository.commitTransaction();
        db.selectTable();
        db.dropTable();
        db.disconnect();
    }
}
