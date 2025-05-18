package com.manageease.model;

public class EmployeeSummary {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String role;
    private String department;
    private String employeeStatus;
    
    
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmployeeStatus() {
		return employeeStatus;
	}
	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}
	@Override
	public String toString() {
		return "EmployeeSummary [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", role=" + role + ", department=" + department + ", employeeStatus=" + employeeStatus + "]";
	}
    
	
}
