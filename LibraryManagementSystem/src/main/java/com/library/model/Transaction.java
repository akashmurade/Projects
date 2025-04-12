package com.library.model;

import com.library.dao.BookDAO;
import com.library.dao.UserDAO;

public class Transaction {
    private int transactionId;
    private int userId;
    private int bookId;
    private java.sql.Date borrowDate;
    private java.sql.Date dueDate;
    private java.sql.Date returnDate;
    private String status; // 'pending', 'approved', 'rejected', 'returned', 'overdue'
    private java.sql.Timestamp createdAt;

    // Getters and Setters
    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public java.sql.Date getBorrowDate() { return borrowDate; }
    public void setBorrowDate(java.sql.Date borrowDate) { this.borrowDate = borrowDate; }

    public java.sql.Date getDueDate() { return dueDate; }
    public void setDueDate(java.sql.Date dueDate) { this.dueDate = dueDate; }

    public java.sql.Date getReturnDate() { return returnDate; }
    public void setReturnDate(java.sql.Date returnDate) { this.returnDate = returnDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public java.sql.Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.sql.Timestamp createdAt) { this.createdAt = createdAt; }

}
