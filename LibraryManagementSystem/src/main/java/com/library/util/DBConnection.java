package com.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/library";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // Update your DB password

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed.");
            e.printStackTrace();
        }
        return conn;
    }
}
