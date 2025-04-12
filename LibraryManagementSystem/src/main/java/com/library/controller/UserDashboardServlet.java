package com.library.controller;

import com.library.dao.BookDAO;
import com.library.dao.TransactionDAO;
import com.library.model.Book;
import com.library.model.Transaction;
import com.library.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/user-dashboard")
public class UserDashboardServlet extends HttpServlet {
    private BookDAO bookDAO;
    private TransactionDAO transactionDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        bookDAO = new BookDAO();
        transactionDAO = new TransactionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");
        request.setAttribute("username", user.getUsername());

        try {
            // Get counts for dashboard cards
            int totalBooks = bookDAO.getAllBooks().size();
            int availableBooks = bookDAO.getAllBooks().stream()
                    .mapToInt(book -> book.getAvailableCopies())
                    .sum();
            
            List<Transaction> userTransactions = transactionDAO.getTransactionsByUser(user.getUserId());
            int borrowedCount = (int) userTransactions.stream()
                    .filter(tx -> "approved".equals(tx.getStatus()))
                    .count();
            int overdueCount = (int) userTransactions.stream()
                    .filter(tx -> "approved".equals(tx.getStatus()) && 
                                 tx.getDueDate().before(new java.util.Date()))
                    .count();

            // Get current borrowings
            List<Transaction> currentBorrowings = userTransactions.stream()
                    .filter(tx -> "approved".equals(tx.getStatus()) || "pending".equals(tx.getStatus()))
                    .toList();

            request.setAttribute("totalBooks", totalBooks);
            request.setAttribute("availableBooks", availableBooks);
            request.setAttribute("borrowedCount", borrowedCount);
            request.setAttribute("overdueCount", overdueCount);
            request.setAttribute("currentBorrowings", currentBorrowings);

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user-dashboard.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");
        if ("search".equals(action)) {
            String query = request.getParameter("query");
            try {
                List<Book> searchResults = bookDAO.searchBooks(query);
                request.setAttribute("searchResults", searchResults);
                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user-dashboard.jsp");
                dispatcher.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error searching books");
            }
        }
    }
}