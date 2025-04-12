package com.library.controller;

import com.library.dao.BookDAO;
import com.library.dao.TransactionDAO;
import com.library.model.Transaction;
import com.library.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/my-borrowings")
public class MyBorrowingsServlet extends HttpServlet {
    private TransactionDAO transactionDAO;
    private BookDAO bookDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        transactionDAO = new TransactionDAO();
        bookDAO = new BookDAO();
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
            // Get current borrowings (pending and approved)
            List<Transaction> currentBorrowings = transactionDAO.getUserTransactionsByStatus(
                user.getUserId(), 
                Arrays.asList("pending", "approved")
            );
            
            // Get past borrowings (returned and rejected)
            List<Transaction> pastBorrowings = transactionDAO.getUserTransactionsByStatus(
                user.getUserId(), 
                Arrays.asList("returned", "rejected")
            );
            
            // Get overdue borrowings
            List<Transaction> overdueBorrowings = transactionDAO.getOverdueTransactions(user.getUserId());
            
            request.setAttribute("currentBorrowings", currentBorrowings);
            request.setAttribute("pastBorrowings", pastBorrowings);
            request.setAttribute("overdueBorrowings", overdueBorrowings);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/my-borrowings.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error loading your borrowings");
            response.sendRedirect("user-dashboard");
        }
    }
}