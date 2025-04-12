package com.library.model;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private String isbn;
    private String category;
    private int totalCopies;
    private int availableCopies;
    private java.sql.Timestamp addedAt;

    // Getters and Setters
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }

    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }

    public java.sql.Timestamp getCreatedAt() { return addedAt; }
    public void setCreatedAt(java.sql.Timestamp createdAt) { this.addedAt = createdAt; }

}
