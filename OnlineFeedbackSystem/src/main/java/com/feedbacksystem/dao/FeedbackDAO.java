package com.feedbacksystem.dao;

import com.feedbacksystem.models.Feedback;
import com.feedbacksystem.config.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackDAO {
    
    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection(); // Assuming DatabaseConnection handles connection pooling
    }

    // Add Feedback
    public boolean addFeedback(Feedback feedback) throws SQLException {
        String sql = "INSERT INTO feedback (user_id, question_id, rating, created_at) VALUES (?, ?, ?, NOW()) ON DUPLICATE KEY UPDATE rating = VALUES(rating), created_at = NOW()";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, feedback.getUserId());
            stmt.setInt(2, feedback.getQuestionId());
            stmt.setInt(3, feedback.getRating());
            return stmt.executeUpdate() > 0;
        }
    }

    // Get All Feedback
    public List<Feedback> getAllFeedback() throws SQLException {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT id, user_id, question_id, rating, created_at FROM feedback ORDER BY created_at DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Feedback feedback = new Feedback(
                    rs.getInt("user_id"),
                    rs.getInt("question_id"),
                    rs.getInt("rating")
                );
                feedback.setId(rs.getInt("id"));
                feedback.setCreatedAt(rs.getTimestamp("created_at"));
                feedbackList.add(feedback);
            }
        }
        return feedbackList;
    }

    // Get Feedback for a Specific User
    public List<Feedback> getFeedbackByUser(int userId) throws SQLException {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT id, user_id, question_id, rating, created_at FROM feedback WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Feedback feedback = new Feedback(
                        rs.getInt("user_id"),
                        rs.getInt("question_id"),
                        rs.getInt("rating")
                    );
                    feedback.setId(rs.getInt("id"));
                    feedback.setCreatedAt(rs.getTimestamp("created_at"));
                    feedbackList.add(feedback);
                }
            }
        }
        return feedbackList;
    }
 // Get average rating and total responses per question
    public List<Map<String, Object>> getAverageRatingPerQuestion() throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();
        String sql = "SELECT f.question_id, q.question, ROUND(AVG(f.rating),2) AS avg_rating, COUNT(f.user_id) AS total_responses " +
                     "FROM feedback f " +
                     "JOIN questions q ON f.question_id = q.id " +
                     "GROUP BY f.question_id ORDER BY avg_rating DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("question_id", rs.getInt("question_id"));
                row.put("question", rs.getString("question")); // Fixing null issue
                row.put("avg_rating", rs.getDouble("avg_rating"));
                row.put("total_responses", rs.getInt("total_responses"));
                results.add(row);
            }
        }
        return results;
    }

    // Get average rating and total responses per user
    public List<Map<String, Object>> getAverageRatingPerUser() throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();
        String sql = "SELECT f.user_id, u.name AS username, ROUND(AVG(f.rating),2) AS avg_rating, COUNT(f.question_id) AS total_responses " +
                     "FROM feedback f " +
                     "JOIN users u ON f.user_id = u.id " +
                     "GROUP BY f.user_id ORDER BY avg_rating DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("user_id", rs.getInt("user_id"));
                row.put("username", rs.getString("username")); // Fixing null issue
                row.put("avg_rating", rs.getDouble("avg_rating"));
                row.put("total_responses", rs.getInt("total_responses"));
                results.add(row);
            }
        }
        return results;
    }

}
