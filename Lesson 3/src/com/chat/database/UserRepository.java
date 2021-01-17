package com.chat.database;

import com.chat.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepository<T extends User> {

    public void create(T user) {
        try {
            Connection connection = ConnectionService.connect();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO chat.users (nickname, email, password) VALUES (?, ?, ?)"
            );
            statement.setString(1, user.getNickname());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("SWW", e);
        }
    }

    public boolean del(String nickname) {
        try {
            Connection connection = ConnectionService.connect();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM users WHERE nickname = (?)"
            );
            statement.setString(1, nickname);
            statement.executeUpdate();
            connection.commit();
            return true;
        } catch (Exception e) {
            throw new RuntimeException("SWW", e);
        }
    }

    public List<User> find(String email) {
        try {
            Connection connection = ConnectionService.connect();
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM chat.users WHERE email = (?)");
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            List<User> user = new ArrayList<>();
            while ((rs.next())) {
                user.add(
                        new User(
                                rs.getString("nickname"),
                                rs.getString("email"),
                                rs.getString("password")
                        ));
            }
            return user;
        } catch (Exception e) {
            throw new RuntimeException("SWW", e);
        }
    }

    public List<User> findAll() {
        try {
            Connection connection = ConnectionService.connect();
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM chat.users");
            ResultSet rs = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(
                        new User(
                                rs.getString("id"),
                                rs.getString("nickname"),
                                rs.getString("email"),
                                rs.getString("password")
                        ));
            }
            return users;
        } catch (Exception e) {
            throw new RuntimeException("SWW", e);
        }
    }

}
