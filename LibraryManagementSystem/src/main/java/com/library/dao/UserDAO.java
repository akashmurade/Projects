package com.library.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.library.model.User;
import com.library.util.DBConnection;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {

    // Register a new user

	public boolean registerUser(User user) {
	    String sql = "INSERT INTO users (name, username, hashed_password, email, role) VALUES (?, ?, ?, ?, ?)";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        String hashed = BCrypt.hashpw(user.getHashedPassword(), BCrypt.gensalt());

	        stmt.setString(1, user.getName());
	        stmt.setString(2, user.getUsername());
	        stmt.setString(3, hashed);
	        stmt.setString(4, user.getEmail());
	        stmt.setString(5, user.getRole());

	        return stmt.executeUpdate() > 0;

	    } catch (SQLIntegrityConstraintViolationException e) {
	        // This handles duplicate username or email nicely
	        System.err.println("Duplicate entry (username or email): " + e.getMessage());
	        return false;

	    } catch (Exception e) {
	        // Other unknown exceptions
	        e.printStackTrace();
	        return false;
	    }
	}


    // Login / Authenticate user
    public User loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("hashed_password");
                if (BCrypt.checkpw(password, storedHash)) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setName(rs.getString("name"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY user_id DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }
        }
        return users;
    }

    public List<User> getRecentUsers(int limit) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY user_id DESC LIMIT ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }
        }
        return users;
    }

    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET name=?, username=?, email=?, role=? WHERE user_id=?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole());
            stmt.setInt(5, user.getUserId());
            
            return stmt.executeUpdate() > 0;
        }
    }

}
