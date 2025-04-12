package com.feedbacksystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.feedbacksystem.dao.FeedbackDAO;
@WebServlet("/ViewResultsServlet")
public class ViewResultsServlet extends HttpServlet {

    private boolean isAdminLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("adminUser") != null;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Check if admin is logged in
        if (!isAdminLoggedIn(request)) {
            response.sendRedirect("AdminLoginServlet");
            return;
        }

        try {
            FeedbackDAO feedbackDAO = new FeedbackDAO();

            out.println("<html><head><title>Feedback Results</title>");
            out.println("<link rel='stylesheet' type='text/css' href='styles.css'>"); // Linking external CSS file
            out.println("</head><body>");
            out.println("<div class='results-container'>");
            out.println("<h2>Feedback Results</h2>");

            // Fetch and display overall rating per question
            out.println("<h3>Overall Rating by Question</h3>");
            out.println("<table class='results-table'>");
            out.println("<tr><th>Question ID</th><th>Question</th><th>Average Rating</th><th>Total Responses</th></tr>");
            for (Map<String, Object> row : feedbackDAO.getAverageRatingPerQuestion()) {
                out.println("<tr><td>" + row.get("question_id") + "</td><td>" + row.get("question") + "</td><td>" +
                            row.get("avg_rating") + "</td><td>" + row.get("total_responses") + "</td></tr>");
            }
            out.println("</table>");

            // Fetch and display overall rating per user
            out.println("<h3>Overall Rating by User</h3>");
            out.println("<table class='results-table'>");
            out.println("<tr><th>User ID</th><th>Username</th><th>Average Rating</th><th>Total Responses</th></tr>");
            for (Map<String, Object> row : feedbackDAO.getAverageRatingPerUser()) {
                out.println("<tr><td>" + row.get("user_id") + "</td><td>" + row.get("username") + "</td><td>" +
                            row.get("avg_rating") + "</td><td>" + row.get("total_responses") + "</td></tr>");
            }
            out.println("</table>");

            out.println("<a class='btn' href='AdminDashboardServlet'>Back to Admin Panel</a>");
            out.println("</div>");
            out.println("</body></html>");

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p class='error'>Error loading feedback data.</p>");
        }
    }
}

