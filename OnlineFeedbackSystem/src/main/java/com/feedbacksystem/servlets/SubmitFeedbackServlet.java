package com.feedbacksystem.servlets;

import com.feedbacksystem.dao.FeedbackDAO;
import com.feedbacksystem.models.Feedback;
import com.feedbacksystem.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;

@WebServlet("/SubmitFeedbackServlet")
public class SubmitFeedbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("UserLoginServlet");
            return;
        }

        User user = (User) session.getAttribute("user");
        int userId = user.getId();

        boolean success = false;

        try {
            FeedbackDAO feedbackDAO = new FeedbackDAO();
            Enumeration<String> parameterNames = request.getParameterNames();

            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                if (paramName.startsWith("rating_")) {
                    try {
                        int questionId = Integer.parseInt(paramName.substring(7));
                        int rating = Integer.parseInt(request.getParameter(paramName));

                        if (rating >= 1 && rating <= 5) {
                            Feedback feedback = new Feedback(userId, questionId, rating);
                            success = feedbackDAO.addFeedback(feedback);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }

        // Dynamically generate response page
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Feedback Submission</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; text-align: center; padding: 50px; }");
        out.println(".container { max-width: 500px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px; box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1); }");
        out.println("h2 { color: " + (success ? "#4CAF50" : "red") + "; }");
        out.println(".btn { display: inline-block; margin-top: 20px; padding: 10px 20px; text-decoration: none; border-radius: 5px; color: white; }");
        out.println(".home-btn { background-color: #007bff; }");
        out.println(".home-btn:hover { background-color: #0056b3; }");
        out.println(".retry-btn { background-color: #dc3545; }");
        out.println(".retry-btn:hover { background-color: #c82333; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        
        if (success) {
            out.println("<h2>Thank You!</h2>");
            out.println("<p>Your feedback has been submitted successfully.</p>");
            out.println("<a href='index.html' class='btn home-btn'>Go to Home</a>");
        } else {
            out.println("<h2>Oops! Something went wrong.</h2>");
            out.println("<p>We couldn't process your feedback. Please try again.</p>");
            out.println("<a href='GiveFeedbackServlet' class='btn retry-btn'>Retry</a>");
        }

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }
}
