package com.feedbacksystem.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/feedback"; // Update DB name if needed
    private static final String USER = "root"; // Change to your MySQL username
    private static final String PASSWORD = "root"; // Change to your MySQL password

    // Private constructor to prevent instantiation
    private DBConnection() {}

    // Returns a new connection every time
    public static Connection getConnection() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found!", e);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database!", e);
        }
    }
}
