package com.library.controller;

import com.library.dao.UserDAO;
import com.library.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    // 👉 Handles GET request to show the register form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // Forward to: /WEB-INF/jsp/register.jsp
        request.getRequestDispatcher("WEB-INF/jsp/register.jsp").forward(request, response);
    }

    // 👉 Handles POST request from the form
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String name = request.getParameter("fullname"); // matches "name" in input field
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");
        String email = request.getParameter("email");

        // Basic check for password match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match.");
            request.getRequestDispatcher("WEB-INF/jsp/register.jsp").forward(request, response);
            return;
        }

        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setHashedPassword(password); // hashing handled inside DAO
        user.setEmail(email);
        user.setRole("user");

        boolean isRegistered = userDAO.registerUser(user);

        if (isRegistered) {
            request.setAttribute("successMessage", "Registration successful! You can now log in.");
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Username or email already exists.");
            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
        }
    }
}
