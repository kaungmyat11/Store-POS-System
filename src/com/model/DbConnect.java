package com.model;

import java.sql.*;

public class DbConnect {
    private Connection connection;
    private Statement statement;


    public DbConnect() {
        try {
            String URL = "jdbc:postgresql://localhost:5432/pos_system";
            String username = "postgres";
            String password = "admin";
            connection = DriverManager.getConnection(URL, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Connection failure!");
            e.printStackTrace();
        }
    }

    public boolean executeLoginQuery(String query) {
        try {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                connection.close();
                return true;
            } else {
                connection.close();
                return false;
            }
        } catch (SQLException e) {
            System.out.println("ERROR in SQL : " + e);
            return false;
        }
    }
}
