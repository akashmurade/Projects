package com.library.controller;

import com.library.dao.BookDAO;
import com.library.dao.TransactionDAO;
import com.library.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

@WebServlet("/borrow-book")
public class BorrowBookServlet extends HttpServlet {
    private BookDAO bookDAO;
    private TransactionDAO transactionDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        bookDAO = new BookDAO();
        transactionDAO = new TransactionDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        try {
            // Check if book is available
            if (bookDAO.getBookById(bookId).getAvailableCopies() <= 0) {
                session.setAttribute("error", "Book is not available for borrowing");
                response.sendRedirect("browse-books");
                return;
            }

            // Create transaction
            java.util.Date currentDate = new java.util.Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, 14); // 2 weeks borrowing period
            
            Date borrowDate = new Date(currentDate.getTime());
            Date dueDate = new Date(calendar.getTimeInMillis());

            if (transactionDAO.createBorrowRequest(user.getUserId(), bookId, borrowDate, dueDate)) {
                // Decrease available copies
                bookDAO.decreaseAvailableCopies(bookId);
                session.setAttribute("success", "Borrow request submitted successfully");
            } else {
                session.setAttribute("error", "Failed to submit borrow request");
            }
            
            response.sendRedirect("browse-books");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error processing borrow request");
            response.sendRedirect("browse-books");
        }
    }
}