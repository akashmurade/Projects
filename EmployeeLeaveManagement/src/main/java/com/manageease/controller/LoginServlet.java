package com.manageease.controller;

import com.manageease.dao.EmployeeDAO;
import com.manageease.model.Employee;
import com.manageease.util.AuthUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private EmployeeDAO employeeDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        employeeDAO = new EmployeeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect to login page if not authenticated
        if (AuthUtil.isLoggedIn(request)) {
            redirectToDashboard(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Employee employee = employeeDAO.authenticate(username, password);

        if (employee != null) {
            // Create session and store employee details
            HttpSession session = request.getSession();
            session.setAttribute("employeeId", employee.getEmployeeId());
            session.setAttribute("username", employee.getUsername());
            session.setAttribute("role", employee.getRole());
            
            // Redirect based on role
            redirectToDashboard(request, response);
        } else {
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }

    private void redirectToDashboard(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        
        String dashboardPage;
        switch (role) {
            case "Admin":
                dashboardPage = "/admin/dashboard";
                break;
            case "HR":
                dashboardPage = "/WEB-INF/views/hr/dashboard.jsp";
                break;
            case "Manager":
                dashboardPage = "/WEB-INF/views/manager/dashboard.jsp";
                break;
            default:
                dashboardPage = "/employee/dashboard";
        }
        
        response.sendRedirect(request.getContextPath() + dashboardPage);
    }
}