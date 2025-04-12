package com.feedbacksystem.servlets;

import com.feedbacksystem.dao.UserDAO;
import com.feedbacksystem.models.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        renderLoginPage(response, null);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userDAO.getUserByEmail(email);
        
        if (user == null) {
            // If user does not exist, show registration option
            renderLoginPage(response, "User not found.");
        } else if (userDAO.validatePassword(password, user.getPassword())) {
            // Successful login
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("GiveFeedbackServlet");
        } else {
            // Invalid password
            renderLoginPage(response, "Invalid email or password. Please try again.");
        }
    }

    private void renderLoginPage(HttpServletResponse response, String errorMessage) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>User Login</title>");
        out.println("<link rel='stylesheet' href='styles.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1>User Login</h1>");
        out.println("<p>Enter your email and password to log in.</p>");

        if (errorMessage != null) {
            out.println("<p style='color: red;'>" + errorMessage + "</p>");
        }

        out.println("<form action='UserLoginServlet' method='post'>");
        out.println("<div class='input-group'>");
        out.println("<label for='email'>Email:</label>");
        out.println("<input type='email' name='email' required>");
        out.println("</div>");

        out.println("<div class='input-group'>");
        out.println("<label for='password'>Password:</label>");
        out.println("<input type='password' name='password' required>");
        out.println("</div>");

        out.println("<input type='submit' class='btn' value='Login'>");
        out.println("</form>");

        // Registration link below the form
        out.println("<p>New user? <a href='UserRegisterServlet'>Register here</a></p>");

        out.println("<p class='back-link'><a href='index.html'>Back to Home</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
