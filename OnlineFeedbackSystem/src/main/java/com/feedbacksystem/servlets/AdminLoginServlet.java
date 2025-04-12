package com.feedbacksystem.servlets;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        renderLoginPage(response, null);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        String correctPassword = "admin123";

        if (password.equals(correctPassword)) {
            // Create a session for the admin
            request.getSession().setAttribute("adminUser", "true");

            // Redirect to the admin dashboard
            response.sendRedirect("AdminDashboardServlet");
        } else {
            renderLoginPage(response, "Incorrect password. Please try again.");
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
        out.println("<title>Admin Login</title>");
        out.println("<link rel='stylesheet' href='styles.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1>Admin Login</h1>");
        out.println("<p>Enter your password to access the admin panel.</p>");

        if (errorMessage != null) {
            out.println("<p style='color: red;'>" + errorMessage + "</p>");
        }

        out.println("<form action='AdminLoginServlet' method='post'>");
        out.println("<div class='input-group'>");
        out.println("<label for='password'>Password:</label>");
        out.println("<input type='password' name='password' required>");
        out.println("</div>");
        out.println("<input type='submit' class='btn admin-btn' value='Login'>");
        out.println("</form>");

        // Back to Home Link
        out.println("<p class='back-link'><a href='index.html'>Back to Home</a></p>");

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
