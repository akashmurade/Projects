package com.feedbacksystem.servlets;

import com.feedbacksystem.dao.UserDAO;
import com.feedbacksystem.models.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        renderRegisterPage(response, null);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (userDAO.getUserByEmail(email) != null) {
            renderRegisterPage(response, "Email already exists! Try logging in.");
            return;
        }

        User user = new User(name, email, password);
        boolean success = userDAO.registerUser(user);

        if (success) {
            response.sendRedirect("UserLoginServlet");
        } else {
            renderRegisterPage(response, "Registration failed! Please try again.");
        }
    }

    private void renderRegisterPage(HttpServletResponse response, String errorMessage) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>User Registration</title>");
        out.println("<link rel='stylesheet' href='styles.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1>User Registration</h1>");
        out.println("<p>Fill in the details to create an account.</p>");

        if (errorMessage != null) {
            out.println("<p style='color: red;'>" + errorMessage + "</p>");
        }

        out.println("<form action='UserRegisterServlet' method='post'>");
        out.println("<div class='input-group'>");
        out.println("<label for='name'>Name:</label>");
        out.println("<input type='text' name='name' required>");
        out.println("</div>");

        out.println("<div class='input-group'>");
        out.println("<label for='email'>Email:</label>");
        out.println("<input type='email' name='email' required>");
        out.println("</div>");

        out.println("<div class='input-group'>");
        out.println("<label for='password'>Password:</label>");
        out.println("<input type='password' name='password' required>");
        out.println("</div>");

        out.println("<input type='submit' class='btn' value='Register'>");
        out.println("</form>");

        out.println("<p class='back-link'><a href='UserLoginServlet'>Already have an account? Login</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
