package com.feedbacksystem.servlets;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@WebServlet("/AdminDashboardServlet")
public class AdminDashboardServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("adminUser") == null) {
            // Redirect to login if the session does not exist
            response.sendRedirect("AdminLoginServlet");
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Admin Dashboard</title>");
        out.println("<link rel='stylesheet' href='styles.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1>Welcome, Admin!</h1>");
        out.println("<p>Manage feedback and view results.</p>");

        // Dashboard options
        out.println("<div class='button-container'>");
        out.println("<a href='manage-questions' class='btn'>Manage Questions</a>");
        out.println("<a href='ViewResultsServlet' class='btn'>View Feedback Results</a>");
        out.println("<a href='LogoutServlet' class='btn admin-btn'>Logout</a>");
        out.println("</div>");

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
