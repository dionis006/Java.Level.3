package com.chat.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {
    private ConnectionService(){
    }

    public static Connection connect(){
        try {
            return DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/chat?serverTimezone=Europe/Moscow", "root", "root");
        } catch (SQLException throwables) {
            throw new RuntimeException("SWW", throwables);
        }
    }
}
