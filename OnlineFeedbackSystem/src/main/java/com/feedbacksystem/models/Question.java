package com.feedbacksystem.models;
import java.sql.Timestamp;

public class Question {
    private int id;
    private String question;
    private String subjectName;  // Expose only the name
    private String status;
    private Timestamp createdAt;

    public Question(int id, String question, String subjectName, String status, Timestamp createdAt) {
        this.id = id;
        this.question = question;
        this.subjectName = subjectName;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;  // Still keep ID internally
    }

    public String getQuestion() {
        return question;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +  // ID is still here for backend operations
                ", question='" + question + '\'' +
                ", subjectName='" + subjectName + '\'' +  // Display Name, Not ID
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
