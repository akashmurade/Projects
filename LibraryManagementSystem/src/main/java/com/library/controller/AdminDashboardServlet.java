package com.library.controller;

import com.library.dao.BookDAO;
import com.library.dao.TransactionDAO;
import com.library.dao.UserDAO;
import com.library.model.Book;
import com.library.model.Transaction;
import com.library.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin-dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private BookDAO bookDAO;
    private TransactionDAO transactionDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        bookDAO = new BookDAO();
        transactionDAO = new TransactionDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!"librarian".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        String action = request.getParameter("action");
        try {
        	if ("manage-books".equals(action)) {
        	    List<Book> allBooks = bookDAO.getAllBooks();
        	    request.setAttribute("books", allBooks);
        	    request.setAttribute("includePage", "admin-books.jsp");
        	}
        	else if ("manage-users".equals(action)) {
        	    List<User> allUsers = userDAO.getAllUsers();
        	    request.setAttribute("users", allUsers);
        	    request.setAttribute("includePage", "admin-users.jsp");
        	}
        	else if ("view-transactions".equals(action)) {
        	    List<Transaction> allTransactions = transactionDAO.getAllTransactions();
        	    request.setAttribute("transactions", allTransactions);
        	    request.setAttribute("includePage", "admin-transactions.jsp");
        	}

            // Default dashboard view
            int totalBooks = bookDAO.getAllBooks().size();
            int availableBooks = bookDAO.getAllBooks().stream()
                    .mapToInt(Book::getAvailableCopies)
                    .sum();
            
            List<Transaction> transactions = transactionDAO.getAllTransactions();
            int borrowedCount = (int) transactions.stream()
                    .filter(tx -> "approved".equals(tx.getStatus()))
                    .count();
            int overdueCount = (int) transactions.stream()
                    .filter(tx -> "approved".equals(tx.getStatus()) && 
                                 tx.getDueDate().before(new java.util.Date()))
                    .count();
            int pendingRequests = (int) transactions.stream()
                    .filter(tx -> "pending".equals(tx.getStatus()))
                    .count();

            List<Transaction> recentTransactions = transactions.size() > 5 ? 
                    transactions.subList(0, 5) : transactions;
            List<User> recentUsers = userDAO.getRecentUsers(5);

            request.setAttribute("totalBooks", totalBooks);
            request.setAttribute("availableBooks", availableBooks);
            request.setAttribute("borrowedCount", borrowedCount);
            request.setAttribute("overdueCount", overdueCount);
            request.setAttribute("pendingRequests", pendingRequests);
            request.setAttribute("transactions", recentTransactions);
            request.setAttribute("recentUsers", recentUsers);

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/admin-dashboard.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        try {
            switch (action) {
                case "approve":
                    int approveId = Integer.parseInt(request.getParameter("transactionId"));
                    transactionDAO.updateTransactionStatus(approveId, "approved", null);
                    session.setAttribute("success", "Transaction approved successfully");
                    break;
                case "reject":
                    int rejectId = Integer.parseInt(request.getParameter("transactionId"));
                    transactionDAO.updateTransactionStatus(rejectId, "rejected", null);
                    session.setAttribute("success", "Transaction rejected successfully");
                    break;
                case "return":
                    int returnId = Integer.parseInt(request.getParameter("transactionId"));
                    transactionDAO.updateTransactionStatus(returnId, "returned", 
                        new java.sql.Date(System.currentTimeMillis()));

                    // Increase available copies
                    try {
                        int bookId = transactionDAO.getBookIdByTransaction(returnId);
                        bookDAO.increaseAvailableCopies(bookId);
                    } catch (Exception e) {
                        e.printStackTrace();
                        session.setAttribute("error", "Failed to update book availability");
                        response.sendRedirect("admin-dashboard?action=view-transactions");
                        return;
                    }

                    session.setAttribute("success", "Book marked as returned");
                    break;

                case "add-book":
                    Book newBook = new Book();
                    newBook.setTitle(request.getParameter("title"));
                    newBook.setAuthor(request.getParameter("author"));
                    newBook.setIsbn(request.getParameter("isbn"));
                    newBook.setCategory(request.getParameter("category"));
                    newBook.setTotalCopies(Integer.parseInt(request.getParameter("totalCopies")));
                    newBook.setAvailableCopies(Integer.parseInt(request.getParameter("availableCopies")));
                    
                    if (bookDAO.addBook(newBook)) {
                        session.setAttribute("success", "Book added successfully");
                    } else {
                        session.setAttribute("error", "Failed to add book");
                    }
                    break;
                case "update-book":
                    Book updatedBook = new Book();
                    updatedBook.setBookId(Integer.parseInt(request.getParameter("bookId")));
                    updatedBook.setTitle(request.getParameter("title"));
                    updatedBook.setAuthor(request.getParameter("author"));
                    updatedBook.setIsbn(request.getParameter("isbn"));
                    updatedBook.setCategory(request.getParameter("category"));
                    updatedBook.setTotalCopies(Integer.parseInt(request.getParameter("totalCopies")));
                    updatedBook.setAvailableCopies(Integer.parseInt(request.getParameter("availableCopies")));
                    
                    if (bookDAO.updateBook(updatedBook)) {
                        session.setAttribute("success", "Book updated successfully");
                    } else {
                        session.setAttribute("error", "Failed to update book");
                    }
                    break;
                case "delete-book":
                    int bookId = Integer.parseInt(request.getParameter("bookId"));
                    if (bookDAO.deleteBook(bookId)) {
                        session.setAttribute("success", "Book deleted successfully");
                    } else {
                        session.setAttribute("error", "Failed to delete book");
                    }
                    break;
                case "update-user":
                    User updatedUser = new User();
                    updatedUser.setUserId(Integer.parseInt(request.getParameter("userId")));
                    updatedUser.setName(request.getParameter("name"));
                    updatedUser.setUsername(request.getParameter("username"));
                    updatedUser.setEmail(request.getParameter("email"));
                    updatedUser.setRole(request.getParameter("role"));
                    
                    if (userDAO.updateUser(updatedUser)) {
                        session.setAttribute("success", "User updated successfully");
                    } else {
                        session.setAttribute("error", "Failed to update user");
                    }
                    break;
            }
            response.sendRedirect("admin-dashboard?action=" + request.getParameter("redirectTo"));
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error processing request: " + e.getMessage());
            response.sendRedirect("admin-dashboard");
        }
    }
}