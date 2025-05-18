package com.manageease.dao;

import com.manageease.model.Employee;
import com.manageease.model.EmployeeSummary;
import com.manageease.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

public class EmployeeDAO {
    private Connection connection;

    public EmployeeDAO() {
        this.connection = DBConnection.getConnection();
    }	

    public Employee authenticate(String username, String password) {
        Employee employee = null;
        String query = "SELECT * FROM Employee WHERE username = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                
                // Verify the password against the stored BCrypt hash
                if (BCrypt.checkpw(password, storedHash)) {
                    employee = new Employee();
                    employee.setEmployeeId(rs.getInt("employee_id"));
                    employee.setFirstName(rs.getString("first_name"));
                    employee.setLastName(rs.getString("last_name"));
                    employee.setUsername(rs.getString("username"));
                    employee.setRole(rs.getString("role"));
                    
                    // Update last login
                    updateLastLogin(employee.getEmployeeId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return employee;
    }

    private void updateLastLogin(int employeeId) {
        String query = "UPDATE Employee SET last_login = CURRENT_TIMESTAMP WHERE employee_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, employeeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to hash passwords when creating/updating users
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    
    public boolean addEmployee(Employee employee) {
        String query = "INSERT INTO Employee (first_name, last_name, email, phone_number, dob, gender, job_title, department, employee_type, date_of_joining, manager_id, username, password_hash, role, employee_status) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getEmail());
            stmt.setString(4, employee.getPhoneNumber());
            stmt.setDate(5, Date.valueOf(employee.getDob()));
            stmt.setString(6, employee.getGender());
            stmt.setString(7, employee.getJobTitle());
            stmt.setString(8, employee.getDepartment());
            stmt.setString(9, employee.getEmployeeType());
            stmt.setDate(10, Date.valueOf(employee.getDateOfJoining()));
            if (employee.getManagerId() != null) {
                stmt.setInt(11, employee.getManagerId());
            } else {
                stmt.setNull(11, Types.INTEGER);
            }
            stmt.setString(12, employee.getUsername());
            stmt.setString(13, employee.getPasswordHash());
            stmt.setString(14, employee.getRole());
            stmt.setString(15, employee.getEmployeeStatus());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<EmployeeSummary> getAllEmployeeSummaries() {
        List<EmployeeSummary> summaries = new ArrayList<>();
        String query = "SELECT employee_id, first_name, last_name, role, department, employee_status FROM Employee";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                EmployeeSummary summary = new EmployeeSummary();
                summary.setEmployeeId(rs.getInt("employee_id"));
                summary.setFirstName(rs.getString("first_name"));
                summary.setLastName(rs.getString("last_name"));
                summary.setRole(rs.getString("role"));
                summary.setDepartment(rs.getString("department"));
                summary.setEmployeeStatus(rs.getString("employee_status"));

                summaries.add(summary);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return summaries;
    }

    public boolean deleteEmployee(int employeeId) {
        String query = "DELETE FROM Employee WHERE employee_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, employeeId);
            
            int rowsAffected = stmt.executeUpdate();
            
            // If one row was affected, it means the deletion was successful
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEmployee(Employee employee) {
        String query = "UPDATE Employee SET first_name = ?, last_name = ?, email = ?, phone_number = ?, dob = ?, gender = ?, " +
                       "job_title = ?, department = ?, employee_type = ?, date_of_joining = ?, manager_id = ?, " +
                       "username = ?, role = ?, employee_status = ? WHERE employee_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getEmail());
            stmt.setString(4, employee.getPhoneNumber());
            stmt.setDate(5, Date.valueOf(employee.getDob()));
            stmt.setString(6, employee.getGender());
            stmt.setString(7, employee.getJobTitle());
            stmt.setString(8, employee.getDepartment());
            stmt.setString(9, employee.getEmployeeType());
            stmt.setDate(10, Date.valueOf(employee.getDateOfJoining()));
            
            if (employee.getManagerId() != null) {
                stmt.setInt(11, employee.getManagerId());
            } else {
                stmt.setNull(11, Types.INTEGER);
            }

            stmt.setString(12, employee.getUsername());
            stmt.setString(13, employee.getRole());
            stmt.setString(14, employee.getEmployeeStatus());
            stmt.setInt(15, employee.getEmployeeId());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Employee getEmployeeById(int employeeId) {
        String query = "SELECT * FROM Employee WHERE employee_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Employee emp = new Employee();
                emp.setEmployeeId(rs.getInt("employee_id"));
                emp.setFirstName(rs.getString("first_name"));
                emp.setLastName(rs.getString("last_name"));
                emp.setEmail(rs.getString("email"));
                emp.setPhoneNumber(rs.getString("phone_number"));
                emp.setDob(rs.getDate("dob").toLocalDate());
                emp.setGender(rs.getString("gender"));
                emp.setJobTitle(rs.getString("job_title"));
                emp.setDepartment(rs.getString("department"));
                emp.setEmployeeType(rs.getString("employee_type"));
                emp.setDateOfJoining(rs.getDate("date_of_joining").toLocalDate());
                int managerId = rs.getInt("manager_id");
                emp.setManagerId(rs.wasNull() ? null : managerId);
                emp.setUsername(rs.getString("username"));
                emp.setPasswordHash(rs.getString("password_hash"));
                emp.setRole(rs.getString("role"));
                emp.setEmployeeStatus(rs.getString("employee_status"));
                return emp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<EmployeeSummary> getSearchedEmployees(String search) {
        List<EmployeeSummary> summaries = new ArrayList<>();

        // Build the SQL with LIKE for name & department, and = for numeric employee_id
        String query = "SELECT employee_id, first_name, last_name, role, department, employee_status " +
                       "FROM Employee " +
                       "WHERE LOWER(first_name) LIKE ? " +
                       "   OR LOWER(last_name) LIKE ? " +
                       "   OR LOWER(department) LIKE ? " +
                       "   OR employee_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Normalize input for LIKE
            String likeSearch = "%" + search.toLowerCase() + "%";
            stmt.setString(1, likeSearch);
            stmt.setString(2, likeSearch);
            stmt.setString(3, likeSearch);

            // Try to parse search as an int for employee_id, or set to -1 to avoid match
            try {
                stmt.setInt(4, Integer.parseInt(search));
            } catch (NumberFormatException e) {
                stmt.setInt(4, -1); // unlikely to match any real employee_id
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EmployeeSummary summary = new EmployeeSummary();
                    summary.setEmployeeId(rs.getInt("employee_id"));
                    summary.setFirstName(rs.getString("first_name"));
                    summary.setLastName(rs.getString("last_name"));
                    summary.setRole(rs.getString("role"));
                    summary.setDepartment(rs.getString("department"));
                    summary.setEmployeeStatus(rs.getString("employee_status"));

                    summaries.add(summary);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Ideally log this
        }

        return summaries;
    }


}