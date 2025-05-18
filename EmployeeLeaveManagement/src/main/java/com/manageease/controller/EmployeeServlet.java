package com.manageease.controller;

import com.manageease.dao.EmployeeDAO;
import com.manageease.dao.LeaveRequestDAO;
import com.manageease.model.LeaveRequest;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@WebServlet("/employee/*")
public class EmployeeServlet extends HttpServlet {

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

        if (session == null || !"Employee".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getPathInfo();

        if (path == null || path.isEmpty()) {
            path = "/dashboard";
        }

        switch (path) {
            case "/dashboard":
                request.getRequestDispatcher("/WEB-INF/views/employee/dashboard.jsp").forward(request, response);
                break;
            case "/profile":
                break;
            case "/leaves":
            	int empId = (int) session.getAttribute("employeeId");
            	request.setAttribute("leaveBalance", leaveRequestDAO.getLeaveBalance(empId));
            	request.setAttribute("leaveHistory", leaveRequestDAO.getLeaveHistory(empId));
                request.getRequestDispatcher("/WEB-INF/views/employee/leaves.jsp").forward(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/employee/dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        
        if ("/leave/apply".equals(path)) {
            handleLeaveRequest(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleLeaveRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String leaveType = request.getParameter("leaveType");
        String fromDateStr = request.getParameter("fromDate");
        String toDateStr = request.getParameter("toDate");
        String reason = request.getParameter("reason");

        // Parse dates
        LocalDate fromDate = LocalDate.parse(fromDateStr);
        LocalDate toDate = LocalDate.parse(toDateStr);

        // Calculate leave days excluding weekends
        int leaveDays = calculateLeaveDays(fromDate, toDate);

        // Get employee ID from session
        HttpSession session = request.getSession(false);
        Integer employeeId = (Integer) session.getAttribute("employeeId");

        if (employeeId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Prepare model
        LeaveRequest leave = new LeaveRequest();
        leave.setEmployeeId(employeeId);
        leave.setLeaveType(leaveType);
        leave.setStartDate(fromDate);  // Use LocalDate directly
        leave.setEndDate(toDate);      // Use LocalDate directly
        leave.setLeaveDays(leaveDays);
        leave.setReason(reason);
        leave.setLeaveStatus("Pending");
        leave.setAppliedOn(Timestamp.valueOf(LocalDateTime.now()));  // Using LocalDate for applied date

        boolean success = leaveRequestDAO.applyLeave(leave);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/employee/leaves");
        } else {
            request.setAttribute("error", "Failed to submit leave request.");
            request.getRequestDispatcher("/employee/leaves.jsp").forward(request, response);
        }
    }

    private int calculateLeaveDays(LocalDate fromDate, LocalDate toDate) {
        int leaveDays = 0;
        for (LocalDate date = fromDate; !date.isAfter(toDate); date = date.plusDays(1)) {
            DayOfWeek day = date.getDayOfWeek();
            if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
                leaveDays++;
            }
        }
        return leaveDays;
    }

}
