package com.feedbacksystem.servlets;

import com.feedbacksystem.dao.QuestionDAO;
import com.feedbacksystem.dao.SubjectDAO;
import com.feedbacksystem.models.Question;
import com.feedbacksystem.models.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/manage-questions")
public class ManageQuestionsServlet extends HttpServlet {
    private QuestionDAO questionDAO = new QuestionDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();

    private boolean isAdminLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("adminUser") != null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdminLoggedIn(request)) {
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
        out.println("<title>Manage Questions</title>");
        out.println("<link rel='stylesheet' href='styles.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='full-width-container'>");
        out.println("<div class='flex'>");
        out.println("<h1>Manage Questions</h1>");

        out.println("<form action='LogoutServlet' method='get' class='logout-btn'>");
        out.println("<input type='submit' value='Logout' class='btn admin-btn'>");
        out.println("</form>");
        out.println("</div>");
        

        out.println("<div class='input-group'>");
        out.println("<form method='post'>");
        out.println("<label for='question'>Question:</label>");
        out.println("<input type='text' name='question' id='question' required>");
        
        out.println("<label for='subject'>Subject:</label>");
        out.println("<select name='subject' id='subject' required>");
        
        List<Subject> subjects = subjectDAO.getAllSubjects();
        for (Subject subject : subjects) {
            out.println("<option value='" + subject.getName() + "'>" + subject.getName() + "</option>");
        }
        
        out.println("</select>");
        
        out.println("<input type='hidden' name='action' value='add'>");
        out.println("<input type='submit' value='Add Question' class='btn'>");
        out.println("</form>");
        out.println("</div>");

        List<Question> questions = questionDAO.getAllQuestions();
        if (questions.isEmpty()) {
            out.println("<p>No questions found.</p>");
        } else {
            out.println("<table class='styled-table'>");
            out.println("<tr><th>Question</th><th>Subject</th><th>Status</th><th>Actions</th></tr>");
            for (Question q : questions) {
                out.println("<tr>");
                out.println("<td>" + q.getQuestion() + "</td>");
                out.println("<td>" + q.getSubjectName() + "</td>");
                out.println("<td>" + q.getStatus() + "</td>");
                out.println("<td>");
                
                out.println("<form method='post' class='inline-form' onsubmit='return confirm(\"Are you sure you want to delete this question?\")'>");
                out.println("<input type='hidden' name='id' value='" + q.getId() + "'>");
                out.println("<input type='hidden' name='action' value='delete'>");
                out.println("<input type='submit' value='Delete' class='btn'>");
                out.println("</form>");

                out.println("<form method='post' class='inline-form'>");
                out.println("<input type='hidden' name='id' value='" + q.getId() + "'>");
                out.println("<input type='hidden' name='action' value='toggleStatus'>");
                out.println("<input type='submit' value='Toggle Status' class='btn admin-btn'>");
                out.println("</form>");

                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }

        out.println("<p class='back-link'><a href='AdminDashboardServlet'>Back to Dashboard</a></p>");
        
        out.println("</div>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdminLoggedIn(request)) {
            response.sendRedirect("AdminLoginServlet");
            return;
        }

        String action = request.getParameter("action");
        String question = request.getParameter("question");
        String subject = request.getParameter("subject");

        if ("add".equals(action) && question != null && subject != null) {
            questionDAO.addQuestion(question.trim(), subject.trim());
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            questionDAO.deleteQuestion(id);
        } else if ("toggleStatus".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            questionDAO.toggleQuestionStatus(id);
        }

        response.sendRedirect("manage-questions");
    }
}
