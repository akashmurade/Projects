package com.feedbacksystem.dao;

import com.feedbacksystem.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import com.feedbacksystem.config.DBConnection;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        this.connection = DBConnection.getConnection();
    }

    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt())); // Hashing

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean validatePassword(String enteredPassword, String storedHash) {
        return BCrypt.checkpw(enteredPassword, storedHash);
    }
}
