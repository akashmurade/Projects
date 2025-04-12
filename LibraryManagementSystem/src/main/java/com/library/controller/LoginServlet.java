package com.library.controller;

import com.library.dao.UserDAO;
import com.library.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            if ("librarian".equals(user.getRole())) {
                response.sendRedirect("admin-dashboard");
            } else {
                response.sendRedirect("user-dashboard");
            }
            return;
        }
        request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userDAO.loginUser(username, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                
                if ("librarian".equals(user.getRole())) {
                    response.sendRedirect("admin-dashboard");
                } else {
                    response.sendRedirect("user-dashboard");
                }
            } else {
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Login failed. Please try again.");
            request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
        }
    }
}