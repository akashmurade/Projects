package com.manageease.controller;

import javax.servlet.*;
import javax.servlet.http.*;

import com.manageease.dao.EmployeeDAO;
import com.manageease.dao.LeaveRequestDAO;
import com.manageease.model.Employee;
import com.manageease.model.EmployeeSummary;
import com.manageease.model.LeaveRequest;
import com.manageease.model.LeaveRequestView;

import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@WebServlet("/admin/*")  // Capture all requests starting with /admin/
public class AdminServlet extends HttpServlet {
	
	private EmployeeDAO employeeDAO;
	private LeaveRequestDAO leaveRequestDAO;

    @Override
    public void init() throws ServletException {
        employeeDAO = new EmployeeDAO();
        leaveRequestDAO = new LeaveRequestDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if the session exists and if the user is an admin
        if (session == null || !"Admin".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get the requested path after /admin/
        String path = request.getPathInfo();

        // If no specific path is provided, default to /dashboard
        if (path == null || path.isEmpty()) {
            path = "/dashboard";
        }

        // Determine the correct JSP page to forward to based on the path
        switch (path) {
            case "/dashboard":
                request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
                break;
            case "/employees":
                // Forward to the employees page
                List<EmployeeSummary> employeeList = employeeDAO.getAllEmployeeSummaries();
            	request.setAttribute("employeeList", employeeList);
                request.getRequestDispatcher("/WEB-INF/views/admin/employees.jsp").forward(request, response);
                break;
            case "/employees/add":
                request.getRequestDispatcher("/WEB-INF/views/admin/employee_add.jsp").forward(request, response);
                break;
            case "/employees/update":
            	request.setAttribute("employee", employeeDAO.getEmployeeById(Integer.parseInt(request.getParameter("employeeId"))));
            	request.getRequestDispatcher("/WEB-INF/views/admin/employee_edit.jsp").forward(request, response);
            	break;
            case "/employees/search":
            	request.setAttribute("employeeList", employeeDAO.getSearchedEmployees(request.getParameter("query")));
            	request.getRequestDispatcher("/WEB-INF/views/admin/employees.jsp").forward(request, response);
            	break;
            case "/leaves":
            	handleLeaveRequests(request, response);
                request.getRequestDispatcher("/WEB-INF/views/admin/leaves.jsp").forward(request, response);
                break;
            case "/attendance":
                // Forward to the attendance page
                request.getRequestDispatcher("/WEB-INF/views/admin/attendance.jsp").forward(request, response);
                break;
            case "/payroll":
                // Forward to the payroll page
                request.getRequestDispatcher("/WEB-INF/views/admin/payroll.jsp").forward(request, response);
                break;
            case "/logs":
                // Forward to the logs page
                request.getRequestDispatcher("/WEB-INF/views/admin/logs.jsp").forward(request, response);
                break;
            default:
                // Default to dashboard if an unknown path is given
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        if ("/employees/add".equals(path)) {
            handleAddEmployee(request, response);
        } else if ("/employees/delete".equals(path)) {
            handleDeleteEmployee(request, response);
        } else if ("/employees/update".equals(path)) {
            handleUpdateEmployee(request, response);
        } else if ("/leaves".equals(path)) {
        	int leaveId = Integer.parseInt(request.getParameter("leaveId"));
        	String action = request.getParameter("action");
        	int approvedBy = (int) request.getSession().getAttribute("employeeId");
            leaveRequestDAO.handleLeaveApprovalOrRejection(leaveId, action, approvedBy);
            String referer = request.getHeader("Referer");
            if (referer != null) {
                response.sendRedirect(referer); 
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/leaves");
            }

        } else {
            // Handle unknown POST paths
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

	private void handleAddEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extract form data
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String jobTitle = request.getParameter("jobTitle");
        String department = request.getParameter("department");
        String employeeType = request.getParameter("employeeType");
        String dateOfJoining = request.getParameter("dateOfJoining");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String employeeStatus = request.getParameter("employeeStatus");

        // Hash password
        String hashedPassword = EmployeeDAO.hashPassword(password);

        // Create Employee object
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setPhoneNumber(phoneNumber);
        employee.setDob(LocalDate.parse(dob));
        employee.setGender(gender);
        employee.setJobTitle(jobTitle);
        employee.setDepartment(department);
        employee.setEmployeeType(employeeType);
        employee.setDateOfJoining(LocalDate.parse(dateOfJoining));
        employee.setUsername(username);
        employee.setPasswordHash(hashedPassword);
        employee.setRole(role);
        employee.setEmployeeStatus(employeeStatus);

        // Save to database
        boolean success = employeeDAO.addEmployee(employee);

        // Redirect or show error
        if (success) {
            response.sendRedirect(request.getContextPath() + "/admin/employees");
        } else {
            request.setAttribute("error", "Failed to add employee.");
            request.getRequestDispatcher("/WEB-INF/views/admin/employee_add.jsp").forward(request, response);
        }
    }
    
    private void handleDeleteEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeIdStr = request.getParameter("employeeId");

        if (employeeIdStr != null) {
            try {
                int employeeId = Integer.parseInt(employeeIdStr);

                // Create DAO instance and delete the employee
                boolean isDeleted = employeeDAO.deleteEmployee(employeeId);

                if (isDeleted) {
                    // Redirect to the employee list after deletion
                    response.sendRedirect(request.getContextPath() + "/admin/employees");
                } else {
                    // Employee not found or error deleting
                    response.getWriter().write("Error: Employee not found or could not be deleted.");
                }

            } catch (NumberFormatException e) {
                // Handle invalid employeeId format
                response.getWriter().write("Error: Invalid employee ID.");
            }
        } else {
            // Handle missing employeeId
            response.getWriter().write("Error: Employee ID is required.");
        }
    }
    
    private void handleUpdateEmployee(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));

        Employee emp = new Employee();
        emp.setEmployeeId(employeeId);
        emp.setFirstName(request.getParameter("firstName"));
        emp.setLastName(request.getParameter("lastName"));
        emp.setEmail(request.getParameter("email"));
        emp.setPhoneNumber(request.getParameter("phoneNumber"));
        emp.setDob(LocalDate.parse(request.getParameter("dob")));
        emp.setGender(request.getParameter("gender"));
        emp.setJobTitle(request.getParameter("jobTitle"));
        emp.setDepartment(request.getParameter("department"));
        emp.setEmployeeType(request.getParameter("employeeType"));
        emp.setDateOfJoining(LocalDate.parse(request.getParameter("dateOfJoining")));
        emp.setManagerId(request.getParameter("managerId").isEmpty() ? null : Integer.parseInt(request.getParameter("managerId")));
        emp.setUsername(request.getParameter("username"));
        emp.setRole(request.getParameter("role"));
        emp.setEmployeeStatus(request.getParameter("employeeStatus"));

        boolean success = employeeDAO.updateEmployee(emp);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/admin/employees");
        } else {
            request.setAttribute("error", "Failed to update employee.");
            request.setAttribute("employee", emp);
            request.getRequestDispatcher("/WEB-INF/views/admin/edit-employee.jsp").forward(request, response);
        }
    }
    
    private void handleLeaveRequests(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = request.getParameter("status");
        Integer loggedInEmpId = (Integer) request.getSession().getAttribute("employeeId");
        
    	List<LeaveRequestView> leaveList;
    	if (status == null || status.equalsIgnoreCase("all")) {
    		leaveList = leaveRequestDAO.getAllLeaveRequestsWithEmployeeInfo();
    	} else {
    		leaveList = leaveRequestDAO.getLeavesByStatusWithEmployeeInfo(status);
    	}        	
    	request.setAttribute("leaveList", leaveList);
    }

}
