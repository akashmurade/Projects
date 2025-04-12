package com.feedbacksystem.dao;

import com.feedbacksystem.config.DBConnection;
import com.feedbacksystem.models.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    // Get all subjects
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String query = "SELECT id, name FROM subjects";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                subjects.add(new Subject(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    // Get subject ID by name
    public int getSubjectIdByName(String subjectName) {
        String query = "SELECT id FROM subjects WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, subjectName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Subject not found
    }
}
