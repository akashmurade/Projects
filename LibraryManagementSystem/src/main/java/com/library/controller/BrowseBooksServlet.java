package com.library.controller;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/browse-books")
public class BrowseBooksServlet extends HttpServlet {
    private BookDAO bookDAO;

    @Override
    public void init() throws ServletException {
        super.init();
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

        String query = request.getParameter("query");
        String category = request.getParameter("category");

        try {
            List<Book> books;
            if (query != null && !query.isEmpty()) {
                books = bookDAO.searchBooks(query, category);
            } else if (category != null && !category.isEmpty()) {
                books = bookDAO.getBooksByCategory(category);
            } else {
                books = bookDAO.getAllBooks();
            }
            
            request.setAttribute("books", books);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/browse-books.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading books");
        }
    }
}