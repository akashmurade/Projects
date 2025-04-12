package com.library.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.library.model.Book;
import com.library.util.DBConnection;

public class BookDAO {

    public boolean addBook(Book book) {
        String sql = "INSERT INTO books (title, author, isbn, category, total_copies, available_copies) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setString(4, book.getCategory());
            stmt.setInt(5, book.getTotalCopies());
            stmt.setInt(6, book.getAvailableCopies());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // You can log this instead
            return false;
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setCategory(rs.getString("category"));
                book.setTotalCopies(rs.getInt("total_copies"));
                book.setAvailableCopies(rs.getInt("available_copies"));
                book.setCreatedAt(rs.getTimestamp("added_at"));
                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public Book getBookById(int bookId) {
        String sql = "SELECT * FROM books WHERE book_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setCategory(rs.getString("category"));
                book.setTotalCopies(rs.getInt("total_copies"));
                book.setAvailableCopies(rs.getInt("available_copies"));
                book.setCreatedAt(rs.getTimestamp("added_at"));
                return book;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateBook(Book book) {
        String sql = "UPDATE books SET title=?, author=?, isbn=?, category=?, total_copies=?, available_copies=? WHERE book_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setString(4, book.getCategory());
            stmt.setInt(5, book.getTotalCopies());
            stmt.setInt(6, book.getAvailableCopies());
            stmt.setInt(7, book.getBookId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBook(int bookId) {
        String sql = "DELETE FROM books WHERE book_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Book> searchBooks(String query) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR isbn LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchQuery = "%" + query + "%";
            stmt.setString(1, searchQuery);
            stmt.setString(2, searchQuery);
            stmt.setString(3, searchQuery);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setCategory(rs.getString("category"));
                book.setTotalCopies(rs.getInt("total_copies"));
                book.setAvailableCopies(rs.getInt("available_copies"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    
    public List<Book> searchBooks(String query, String category) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE (title LIKE ? OR author LIKE ? OR isbn LIKE ?) " +
                     (category != null && !category.isEmpty() ? "AND category = ?" : "");
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchQuery = "%" + query + "%";
            stmt.setString(1, searchQuery);
            stmt.setString(2, searchQuery);
            stmt.setString(3, searchQuery);
            
            if (category != null && !category.isEmpty()) {
                stmt.setString(4, category);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(extractBook(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> getBooksByCategory(String category) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE category = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                books.add(extractBook(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    private Book extractBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setIsbn(rs.getString("isbn"));
        book.setCategory(rs.getString("category"));
        book.setTotalCopies(rs.getInt("total_copies"));
        book.setAvailableCopies(rs.getInt("available_copies"));
        return book;
    }
    
    public boolean decreaseAvailableCopies(int bookId) {
        String sql = "UPDATE books SET available_copies = available_copies - 1 WHERE book_id = ? AND available_copies > 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean increaseAvailableCopies(int bookId) throws SQLException {
        String sql = "UPDATE books SET available_copies = available_copies + 1 WHERE book_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            return stmt.executeUpdate() > 0;
        }
    }
}
