package com.manageease.dao;

import java.sql.*;
import java.util.*;
import java.sql.Date;

import com.manageease.model.LeaveRequest;
import com.manageease.model.LeaveRequestSummary;
import com.manageease.model.LeaveRequestView;
import com.manageease.util.DBConnection;

public class LeaveRequestDAO {

	private Connection connection;

    public LeaveRequestDAO() {
        this.connection = DBConnection.getConnection();
    }	

    // Submit a new leave request
	
    public boolean applyLeave(LeaveRequest leave) {
        String sql = "INSERT INTO leaverequests (employee_id, leave_type, start_date, end_date, leave_days, reason) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, leave.getEmployeeId());
            stmt.setString(2, leave.getLeaveType());
            stmt.setDate(3, Date.valueOf(leave.getStartDate()));
            stmt.setDate(4, Date.valueOf(leave.getEndDate())); 
            stmt.setInt(5, leave.getLeaveDays());
            stmt.setString(6, leave.getReason());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get leave requests by employee ID
    public List<LeaveRequest> getLeavesByEmployee(int employeeId) {
        List<LeaveRequest> leaves = new ArrayList<>();
        String sql = "SELECT * FROM leaverequests WHERE employee_id = ? ORDER BY applied_on DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LeaveRequest leave = extractLeaveRequest(rs);
                leaves.add(leave);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return leaves;
    }

    // Update leave status
    public boolean updateLeaveStatus(int leaveId, String status, int approvedBy) {
        String sql = "UPDATE leaverequests SET leave_status = ?, approved_by = ?, approval_date = NOW() WHERE leave_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, approvedBy);
            stmt.setInt(3, leaveId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Admin: Get all leave requests
    public List<LeaveRequest> getAllLeaves() {
        List<LeaveRequest> leaves = new ArrayList<>();
        String sql = "SELECT * FROM leaverequests ORDER BY applied_on DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                leaves.add(extractLeaveRequest(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return leaves;
    }

    // Extract LeaveRequest from ResultSet
    private LeaveRequest extractLeaveRequest(ResultSet rs) throws SQLException {
        LeaveRequest leave = new LeaveRequest();
        leave.setLeaveId(rs.getInt("leave_id"));
        leave.setEmployeeId(rs.getInt("employee_id"));
        leave.setLeaveType(rs.getString("leave_type"));
        leave.setStartDate(rs.getDate("start_date").toLocalDate());
        leave.setEndDate(rs.getDate("end_date").toLocalDate());
        leave.setLeaveDays(rs.getInt("leave_days"));
        leave.setLeaveStatus(rs.getString("leave_status"));
        leave.setReason(rs.getString("reason"));
        leave.setAppliedOn(rs.getTimestamp("applied_on"));
        leave.setApprovedBy(rs.getInt("approved_by"));
        leave.setApprovalDate(rs.getTimestamp("approval_date"));
        return leave;
    }
    
    // Fetch leave history for a specific employee
    public List<LeaveRequestSummary> getLeaveHistory(int employeeId) {
        List<LeaveRequestSummary> leaveHistory = new ArrayList<>();
        String sql = "SELECT leave_id, leave_type, start_date, end_date, leave_status FROM leaverequests WHERE employee_id = ? ORDER BY applied_on DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                LeaveRequestSummary leaveSummary = new LeaveRequestSummary();
                leaveSummary.setLeaveId(rs.getInt("leave_id"));
                leaveSummary.setLeaveType(rs.getString("leave_type"));
                leaveSummary.setStartDate(rs.getDate("start_date").toLocalDate());
                leaveSummary.setEndDate(rs.getDate("end_date").toLocalDate());
                leaveSummary.setLeaveStatus(rs.getString("leave_status"));
                
                leaveHistory.add(leaveSummary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaveHistory;
    }
    
    // Fetch Leaves left for a particular employee
    public int getLeaveBalance(int employeeId) {
        String sql = "SELECT leave_balance FROM employee WHERE employee_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("leave_balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

 // Get leave requests by status (case-insensitive)
    public List<LeaveRequest> getLeavesByStatus(String status) {
        List<LeaveRequest> leaves = new ArrayList<>();
        String sql = "SELECT * FROM leaverequests WHERE LOWER(leave_status) = ? ORDER BY applied_on DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status.toLowerCase());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                leaves.add(extractLeaveRequest(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return leaves;
    }

    // Get leave requests by employee AND status
    public List<LeaveRequest> getLeavesByEmployeeAndStatus(int employeeId, String status) {
        List<LeaveRequest> leaves = new ArrayList<>();
        String sql = "SELECT * FROM leaverequests WHERE employee_id = ? AND LOWER(leave_status) = ? ORDER BY applied_on DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            stmt.setString(2, status.toLowerCase());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                leaves.add(extractLeaveRequest(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return leaves;
    }
    
    public List<LeaveRequestView> getAllLeaveRequestsWithEmployeeInfo() {
        String sql = "SELECT lr.leave_id, lr.employee_id, e.first_name, e.last_name, lr.leave_type, lr.start_date, lr.end_date, lr.leave_days, lr.leave_status " +
                     "FROM leaverequests lr JOIN employee e ON lr.employee_id = e.employee_id ORDER BY lr.applied_on DESC";
        List<LeaveRequestView> result = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LeaveRequestView view = new LeaveRequestView();
                view.setLeaveId(rs.getInt("leave_id"));
                view.setEmployeeId(rs.getInt("employee_id"));
                view.setEmployeeName(rs.getString("first_name") + " " + rs.getString("last_name"));
                view.setLeaveType(rs.getString("leave_type"));
                view.setStartDate(rs.getDate("start_date").toLocalDate());
                view.setEndDate(rs.getDate("end_date").toLocalDate());
                view.setLeaveDays(rs.getInt("leave_days"));
                view.setLeaveStatus(rs.getString("leave_status"));

                result.add(view);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    
    public List<LeaveRequestView> getLeavesByStatusWithEmployeeInfo(String status) {
        List<LeaveRequestView> leaves = new ArrayList<>();
        String sql = "SELECT lr.leave_id, lr.employee_id, e.first_name, e.last_name, lr.leave_type, " +
                     "lr.start_date, lr.end_date, lr.leave_days, lr.leave_status " +
                     "FROM leaverequests lr " +
                     "JOIN employee e ON lr.employee_id = e.employee_id " +
                     "WHERE LOWER(lr.leave_status) = ? " +
                     "ORDER BY lr.applied_on DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status.toLowerCase());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LeaveRequestView leave = new LeaveRequestView();
                leave.setLeaveId(rs.getInt("leave_id"));
                leave.setEmployeeId(rs.getInt("employee_id"));
                leave.setEmployeeName(rs.getString("first_name") + " " + rs.getString("last_name"));
                leave.setLeaveType(rs.getString("leave_type"));
                leave.setStartDate(rs.getDate("start_date").toLocalDate());
                leave.setEndDate(rs.getDate("end_date").toLocalDate());
                leave.setLeaveDays(rs.getInt("leave_days"));
                leave.setLeaveStatus(rs.getString("leave_status"));

                leaves.add(leave);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leaves;
    }

    public boolean handleLeaveApprovalOrRejection(int leaveId, String action, int approvedBy) {
        // Declare necessary resources
        PreparedStatement getLeaveStmt = null;
        PreparedStatement updateLeaveStmt = null;
        PreparedStatement updateEmpStmt = null;
        ResultSet rs = null;

        try {
            // Step 1: Get leave request details
            String getLeaveSQL = "SELECT employee_id, leave_days FROM leaverequests WHERE leave_id = ?";
            getLeaveStmt = connection.prepareStatement(getLeaveSQL);
            getLeaveStmt.setInt(1, leaveId);

            rs = getLeaveStmt.executeQuery();
            if (!rs.next()) return false; // No leave request found
            
            int empId = rs.getInt("employee_id");
            int leaveDays = rs.getInt("leave_days");

            // Step 2: Update leave request status
            String updateLeaveSQL = "UPDATE leaverequests SET leave_status = ?, approved_by = ?, approval_date = NOW() WHERE leave_id = ?";
            updateLeaveStmt = connection.prepareStatement(updateLeaveSQL);
            updateLeaveStmt.setString(1, action);
            updateLeaveStmt.setInt(2, approvedBy);
            updateLeaveStmt.setInt(3, leaveId);
            updateLeaveStmt.executeUpdate();

            // Step 3: If approved, update employee's leave records
            if ("Approved".equalsIgnoreCase(action)) {
                String updateEmpSQL = "UPDATE employee SET leave_balance = leave_balance - ?, leaves_taken = leaves_taken + ? WHERE employee_id = ?";
                updateEmpStmt = connection.prepareStatement(updateEmpSQL);
                updateEmpStmt.setInt(1, leaveDays);
                updateEmpStmt.setInt(2, leaveDays);
                updateEmpStmt.setInt(3, empId);
                updateEmpStmt.executeUpdate();
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Clean up resources
            try {
                if (rs != null) rs.close();
                if (getLeaveStmt != null) getLeaveStmt.close();
                if (updateLeaveStmt != null) updateLeaveStmt.close();
                if (updateEmpStmt != null) updateEmpStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }



}
