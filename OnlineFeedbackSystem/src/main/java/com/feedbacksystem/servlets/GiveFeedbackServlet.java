package com.feedbacksystem.servlets;

import com.feedbacksystem.dao.QuestionDAO;
import com.feedbacksystem.models.Question;
import com.feedbacksystem.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/GiveFeedbackServlet")
public class GiveFeedbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("UserLoginServlet");
            return;
        }

        QuestionDAO questionDAO = new QuestionDAO();
        List<Question> activeQuestions = questionDAO.getActiveQuestions();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Give Feedback</title>");
        out.println("<link rel='stylesheet' href='styles.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='full-width-container'>");
        out.println("<h1>Give Feedback</h1>");

        if (activeQuestions.isEmpty()) {
            out.println("<p>No active questions available for feedback.</p>");
        } else {
            out.println("<form action='SubmitFeedbackServlet' method='post'>");
            out.println("<table class='styled-table'>");
            out.println("<tr><th>Question</th><th>Your Feedback</th></tr>");
            for (Question question : activeQuestions) {
                out.println("<tr>");
                out.println("<td>" + question.getQuestion() + "</td>");
                out.println("<td>");
                out.println("<input type='hidden' name='question_id' value='" + question.getId() + "'>");
                out.println("<select name='rating_" + question.getId() + "' required>");
                out.println("<option value=''>Select rating</option>");
                out.println("<option value='1'>1</option>");
                out.println("<option value='2'>2</option>");
                out.println("<option value='3'>3</option>");
                out.println("<option value='4'>4</option>");
                out.println("<option value='5'>5</option>");
                out.println("</select>");
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<input type='submit' class='btn' value='Submit Feedback'>");
            out.println("</form>");
        }

        out.println("<p class='back-link'><a href='UserLoginServlet'>Back to Home</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}