package com.feedbacksystem.models;

import java.sql.Timestamp;

public class Feedback {
    private int id;
    private int userId;
    private int questionId;
    private int rating;
    private Timestamp createdAt;

    // Constructor
    public Feedback(int userId, int questionId, int rating) {
        this.userId = userId;
        this.questionId = questionId;
        this.rating = rating;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
