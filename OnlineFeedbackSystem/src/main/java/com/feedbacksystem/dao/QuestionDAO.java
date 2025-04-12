package com.feedbacksystem.dao;

import com.feedbacksystem.config.DBConnection;
import com.feedbacksystem.models.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private final SubjectDAO subjectDAO;  // Define SubjectDAO once

    public QuestionDAO() {
        this.subjectDAO = new SubjectDAO(); // Initialize in constructor
    }

    // Add a new question
    public boolean addQuestion(String questionText, String subjectName) {
        int subjectId = subjectDAO.getSubjectIdByName(subjectName);

        if (subjectId == -1) {
            System.out.println("❌ Subject not found: " + subjectName);
            return false;
        }

        String query = "INSERT INTO questions (question, subject_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, questionText);
            stmt.setInt(2, subjectId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error adding question: " + questionText);
            e.printStackTrace();
        }
        return false;
    }

    // Retrieve all questions with subject name instead of ID
    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT q.id, q.question, s.name AS subject_name, q.status, q.created_at " +
                       "FROM questions q " +
                       "JOIN subjects s ON q.subject_id = s.id"; // Joining to get subject name

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                questions.add(new Question(
                        rs.getInt("id"),
                        rs.getString("question"),
                        rs.getString("subject_name"), // Subject name instead of ID
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error retrieving questions!");
            e.printStackTrace();
        }
        return questions;
    }

    // Retrieve only active questions
    public List<Question> getActiveQuestions() {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT q.id, q.question, s.name AS subject_name, q.status, q.created_at " +
                       "FROM questions q " +
                       "JOIN subjects s ON q.subject_id = s.id " +
                       "WHERE q.status = 'active'"; // Fetch only active questions

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                questions.add(new Question(
                        rs.getInt("id"),
                        rs.getString("question"),
                        rs.getString("subject_name"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error retrieving active questions!");
            e.printStackTrace();
        }
        return questions;
    }

    // Update a question
    public boolean updateQuestion(int id, String newQuestionText, String subjectName) {
        int subjectId = subjectDAO.getSubjectIdByName(subjectName);

        if (subjectId == -1) {
            System.out.println("❌ Subject not found: " + subjectName);
            return false;
        }

        String query = "UPDATE questions SET question = ?, subject_id = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newQuestionText);
            stmt.setInt(2, subjectId);
            stmt.setInt(3, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error updating question ID " + id);
            e.printStackTrace();
        }
        return false;
    }

    // Delete a question
    public boolean deleteQuestion(int id) {
        String query = "DELETE FROM questions WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error deleting question ID " + id);
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean toggleQuestionStatus(int id) {
        String getStatusQuery = "SELECT status FROM questions WHERE id = ?";
        String updateStatusQuery = "UPDATE questions SET status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement getStatusStmt = conn.prepareStatement(getStatusQuery)) {

            getStatusStmt.setInt(1, id);
            try (ResultSet rs = getStatusStmt.executeQuery()) {
                if (rs.next()) {
                    String currentStatus = rs.getString("status");
                    String newStatus = currentStatus.equals("active") ? "inactive" : "active";

                    try (PreparedStatement updateStmt = conn.prepareStatement(updateStatusQuery)) {
                        updateStmt.setString(1, newStatus);
                        updateStmt.setInt(2, id);
                        return updateStmt.executeUpdate() > 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error toggling status for question ID " + id);
            e.printStackTrace();
        }
        return false;
    }
}
