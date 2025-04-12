package com.library.controller;

import com.library.dao.BookDAO;
import com.library.dao.TransactionDAO;
import com.library.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/return-book")
public class ReturnBookServlet extends HttpServlet {
    private TransactionDAO transactionDAO;
    private BookDAO bookDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        transactionDAO = new TransactionDAO();
        bookDAO = new BookDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        int transactionId = Integer.parseInt(request.getParameter("transactionId"));
        
        try {
            // Update transaction status to returned
            boolean success = transactionDAO.updateTransactionStatus(
                transactionId, 
                "returned", 
                new Date(System.currentTimeMillis())
            );

            if (success) {
                // Increase available copies of the book
                int bookId = transactionDAO.getBookIdByTransaction(transactionId);
                bookDAO.increaseAvailableCopies(bookId);
                
                session.setAttribute("success", "Book returned successfully");
            } else {
                session.setAttribute("error", "Failed to return book");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error processing return request");
        }
        
        response.sendRedirect("my-borrowings");
    }
}