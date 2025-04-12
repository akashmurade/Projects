package com.library.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.library.model.Transaction;
import com.library.util.DBConnection;

public class TransactionDAO {

    public boolean addTransaction(Transaction tx) {
        String sql = "INSERT INTO transactions (user_id, book_id, borrow_date, due_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tx.getUserId());
            stmt.setInt(2, tx.getBookId());
            stmt.setDate(3, tx.getBorrowDate());
            stmt.setDate(4, tx.getDueDate());
            stmt.setString(5, tx.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transaction> getTransactionsByUser(int userId) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction tx = extractTransaction(rs);
                list.add(tx);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Transaction tx = extractTransaction(rs);
                list.add(tx);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateTransactionStatus(int transactionId, String status, Date returnDate) {
        String sql = "UPDATE transactions SET status = ?, return_date = ? WHERE transaction_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setDate(2, returnDate);
            stmt.setInt(3, transactionId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Transaction extractTransaction(ResultSet rs) throws SQLException {
        Transaction tx = new Transaction();
        tx.setTransactionId(rs.getInt("transaction_id"));
        tx.setUserId(rs.getInt("user_id"));
        tx.setBookId(rs.getInt("book_id"));
        tx.setBorrowDate(rs.getDate("borrow_date"));
        tx.setDueDate(rs.getDate("due_date"));
        tx.setReturnDate(rs.getDate("return_date"));
        tx.setStatus(rs.getString("status"));
        tx.setCreatedAt(rs.getTimestamp("created_at"));
        return tx;
    }
    
    public boolean createBorrowRequest(int userId, int bookId, Date borrowDate, Date dueDate) {
        String sql = "INSERT INTO transactions (user_id, book_id, borrow_date, due_date, status) VALUES (?, ?, ?, ?, 'pending')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            stmt.setDate(3, borrowDate);
            stmt.setDate(4, dueDate);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Transaction> getUserTransactionsByStatus(int userId, List<String> statuses) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        
        if (statuses == null || statuses.isEmpty()) {
            return transactions;
        }

        // Create the SQL query with placeholders for statuses
        String placeholders = String.join(",", Collections.nCopies(statuses.size(), "?"));
        String sql = String.format("SELECT * FROM transactions WHERE user_id = ? AND status IN (%s) ORDER BY borrow_date DESC", placeholders);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            for (int i = 0; i < statuses.size(); i++) {
                stmt.setString(i + 2, statuses.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(extractTransaction(rs));
            }
        }
        return transactions;
    }

    public List<Transaction> getOverdueTransactions(int userId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? AND status = 'approved' AND due_date < CURDATE() AND (return_date IS NULL OR return_date > due_date) ORDER BY due_date ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(extractTransaction(rs));
            }
        }
        return transactions;
    }
    
    public int getBookIdByTransaction(int transactionId) throws SQLException {
        String sql = "SELECT book_id FROM transactions WHERE transaction_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("book_id");
            }
        }
        throw new SQLException("Transaction not found");
    }
}
