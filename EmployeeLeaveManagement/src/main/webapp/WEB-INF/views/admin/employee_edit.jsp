<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <title>Edit Employee - ManageEase</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap" rel="stylesheet"/>
    <style>
        body {
            font-family: 'Inter', sans-serif;
        }
    </style>
</head>
<body class="bg-white text-black">
<div class="flex min-h-screen">
    <jsp:include page="sidebar.jsp"/>

    <!-- Main content -->
    <main class="flex-1 p-6 max-w-4xl mx-auto ml-64">
        <!-- Breadcrumb -->
        <div class="text-xs font-normal mb-6 text-black">
            <span>Home</span>
            <i class="fas fa-chevron-right mx-2 text-[10px]"></i>
            <span>Employee Mgmt</span>
            <i class="fas fa-chevron-right mx-2 text-[10px]"></i>
            <span class="font-semibold">Edit Employee</span>
        </div>

        <h2 class="font-semibold text-sm mb-6 text-black">Edit Employee</h2>
        
        <c:if test="${not empty error}">
            <p class="text-red-500 text-sm text-center mb-4">${error}</p>
        </c:if>
        
        <form class="space-y-8" action="update" method="POST">
            <input type="hidden" name="employeeId" value="${employee.employeeId}">
            
            <!-- Personal Information -->
            <section>
                <h3 class="text-xs font-semibold mb-3 text-black">Personal Information</h3>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6 max-w-3xl">
                    <div>
                        <label class="block text-sm font-semibold mb-1" for="firstName">First Name*</label>
                        <input id="firstName" name="firstName" required
                               class="w-full bg-gray-50 text-black text-sm px-4 py-3 border border-transparent focus:outline-none focus:ring-2 focus:ring-pink-400 rounded"
                               type="text" placeholder="Enter first name" value="${employee.firstName}"/>
                    </div>
                    <div>
                        <label class="block text-sm font-semibold mb-1" for="lastName">Last Name*</label>
                        <input id="lastName" name="lastName" required
                               class="w-full bg-gray-50 text-black text-sm px-4 py-3 border border-transparent focus:outline-none focus:ring-2 focus:ring-pink-400 rounded"
                               type="text" placeholder="Enter last name" value="${employee.lastName}"/>
                    </div>
                    <div>
                        <label class="block text-sm font-semibold mb-1" for="dob">Date of Birth*</label>
                        <input id="dob" name="dob" required
                               class="w-full bg-gray-50 text-black text-sm px-4 py-3 border border-transparent focus:outline-none focus:ring-2 focus:ring-pink-400 rounded"
                               type="date" value="${employee.dob}"/>
                    </div>
                    <div>
                        <label class="block text-sm font-semibold mb-1" for="gender">Gender*</label>
                        <select id="gender" name="gender" required
                                class="w-full bg-gray-50 text-black text-sm px-4 py-3 border border-transparent focus:outline-none focus:ring-2 focus:ring-pink-400 rounded">
                            <option value="">Select gender</option>
                            <option value="Male" ${employee.gender == 'Male' ? 'selected' : ''}>Male</option>
                            <option value="Female" ${employee.gender == 'Female' ? 'selected' : ''}>Female</option>
                            <option value="Other" ${employee.gender == 'Other' ? 'selected' : ''}>Other</option>
                        </select>
                    </div>
                </div>
            </section>

            <!-- Contact Information -->
            <section>
                <h3 class="text-xs font-semibold mb-3 text-black">Contact Information</h3>
                <div class="space-y-6 max-w-3xl">
                    <div>
                        <label class="block text-sm font-semibold mb-1" for="email">Email*</label>
                        <input id="email" name="email" required
                               class="w-full bg-gray-50 text-black text-sm px-4 py-3 border border-transparent focus:outline-none focus:ring-2 focus:ring-pink-400 rounded"
                               type="email" placeholder="Enter email address" value="${employee.email}"/>
                    </div>
                    <div>
                        <label class="block text-sm font-semibold mb-1" for="phoneNumber">Phone Number*</label>
                        <input id="phoneNumber" name="phoneNumber" required
                               class="w-full bg-gray-50 text-black text-sm px-4 py-3 border border-transparent focus:outline-none focus:ring-2 focus:ring-pink-400 rounded"
                               type="tel" placeholder="Enter phone number" value="${employee.phoneNumber}"/>
                    </div>
                </div>
            </section>

            <!-- Employment Details -->
            <section>
                <h3 class="text-xs font-semibold mb-3 text-black">Employment Details</h3>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6 max-w-3xl">
                    <div>
                        <label class="block text-sm font-semibold mb-1" for="jobTitle">Job Title*</label>
                        <input id="jobTitle" name="jobTitle" required
                               class="w-full bg-gray-50 text-black text-sm px-4 py-3 border border-transparent focus:outline-none focus:ring-2 focus:ring-pink-400 rounded"
                               type="text" placeholder="Enter job title" value="${employee.jobTitle}"/>
                    </div>
                    <div>
                        <label class="block text-sm font-semibold mb-1" for="department">Department*</label>
                        <select id="department" name="department" required
                                class="w-full bg-gray-50 text-black text-sm px-4 py-3 border border-transparent focus:outline-none focus:ring-2 focus:ring-pink-400 rounded">
                            <option value="">Select department</option>
                            <option value="HR" ${employee.department == 'HR' ? 'selected' : ''}>HR</option>
                            <option value="Finance" ${employee.department == 'Finance' ? 'selected' : ''}>Finance</option>
                            <option value="IT" ${employee.department == 'IT' ? 'selected' : ''}>IT</option>
                            <option value="Marketing" ${employee.department == 'Marketing' ? 'selected' : ''}>Marketing</option>
                            <option value="Operations" ${employee.department == 'Operations' ? 'selected' : ''}>Operations</option>
                            <option value="Sales" ${employee.department == 'Sales' ? 'selected' : ''}>Sales</option>
                        </select>
                    </div>
                    <div>
                        <label class="block text-sm font-semibold mb-1" for="employeeType">Employee Type*</label>
                        <select id="employeeType" name="employeeType" required
                                class="w-full bg-gray-50 text-black text-sm px-4 py-3 border border-transparent focus:outline-none focus:ring-2 focus:ring-pink-400 rounded">
                            <option value="">Select employee type</option>
                            <option value="Full-time" ${employee.employeeType == 'Full-time' ? 'selected' : ''}>Full-time</option>
                            <option value="Part-time" ${employee.employeeType == 'Part-time' ? 'selected' : ''}>Part-time</option>
                            <option value="Contract" ${employee.employeeType == 'Contract' ? 'selected' : ''}>Contract</option>
                            <option value="Intern" ${employee.employeeType == 'Intern' ? 'selected' : ''}>Intern</option>
                        </select>
                    </div>
                    <div>
                        <label class="block text-sm font-semibold mb-1" for="dateOfJoining">Date of Joining*</label>
                        <input id="dateOfJoining" name="dateOfJoining" required
                               class="w-full bg-gray-50 text-black text-sm px-4 py-3 border border-transparent focus:outline-none focus:ring-2 focus:ring-pink-400 rounded"
                               type="date" value="${employee.dateOfJoining}"/>
                    </div>
                    <div>
                        <label class="block text-sm font-semibold mb-1" for="managerId">Manager</label>
                        <select id="managerId" name="managerId"
                                class="w-full bg-gray-50 text-black text-sm px-4 py-3 border border-transparent focus:outline-none focus:ring-2 focus:ring-pink-400 rounded">
                            <option value="">Select manager (optional)</option>
                            <!-- This would be populated from the database in a real application -->
                            <option value="1" ${employee.managerId == 1 ? 'selected' : ''}>John Doe</option>
                            <option value="2" ${employee.managerId == 2 ? 'selected' : ''}>Jane Smith</option>
                        </select>
                    </div>
                    <div>
                        <label class="block text-sm font-semibold mb-1" for="totalLeave">Total Leave Days</label>
                        <input id="totalLeave" name="totalLeave"
                               class="w-full bg-gray-50 text-black text-sm px-4 py-3 border border-transparent focus:outline-none focus:ring-2 focus:ring-pink-400 rounded"
                               type="number" placeholder="Total leave days" value="${employee.totalLeave}"/>
                    </div>
                    <div>
                        <label class="block text-sm font-semibold mb-1" for="leaveTaken">Leave Taken</label>
                        <input id="leaveTaken" name="leaveTaken"
                               class="w-full bg-gray-50 text-black text-sm px-4 py-3 border border-transparent focus:outline-none focus:ring-2 focus:ring-pink-400 rounded"
                               type="number" placeholder="Leave taken" value="${employee.leaveTaken}"/>
                    </div>
                </div>
            </section>

            <!-- Account Information -->
            <section>
                <h3 class="text-xs font-semibold mb-3 text-black">Account Information</h3>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6 max-w-3xl">
                    <div>
                        <label class="block text-sm font-semibold mb-1" for="username">Username*</label>
                        <input id="username" name="username" required
                               class="w-full bg-gray-50 text-black text-sm px-4 py-3 border border-transparent focus:outline-none focus:ring-2 focus:ring-pink-400 rounded"
                               type="text" placeholder="Enter username" value="${employee.username}"/>
                    </div>
                    <div>
                        <label class="block text-sm font-semibold mb-1" for="role">Role*</label>
                        <select id="role" name="role" required
                                class="w-full bg-gray-50 text-black text-sm px-4 py-3 border border-transparent focus:outline-none focus:ring-2 focus:ring-pink-400 rounded">
                            <option value="">Select role</option>
                            <option value="Admin" ${employee.role == 'Admin' ? 'selected' : ''}>Admin</option>
                            <option value="HR" ${employee.role == 'HR' ? 'selected' : ''}>HR</option>
                            <option value="Manager" ${employee.role == 'Manager' ? 'selected' : ''}>Manager</option>
                            <option value="Employee" ${employee.role == 'Employee' ? 'selected' : ''}>Employee</option>
                        </select>
                    </div>
                    <div>
                        <label class="block text-sm font-semibold mb-1" for="employeeStatus">Status*</label>
                        <select id="employeeStatus" name="employeeStatus" required
                                class="w-full bg-gray-50 text-black text-sm px-4 py-3 border border-transparent focus:outline-none focus:ring-2 focus:ring-pink-400 rounded">
                            <option value="Active" ${employee.employeeStatus == 'Active' ? 'selected' : ''}>Active</option>
                            <option value="On Leave" ${employee.employeeStatus == 'On Leave' ? 'selected' : ''}>On Leave</option>
                            <option value="Resigned" ${employee.employeeStatus == 'Resigned' ? 'selected' : ''}>Resigned</option>
                            <option value="Terminated" ${employee.employeeStatus == 'Terminated' ? 'selected' : ''}>Terminated</option>
                        </select>
                    </div>
                </div>
            </section>

            <!-- Buttons -->
            <div class="max-w-3xl flex space-x-4">
                <button type="submit"
                        class="bg-pink-500 text-white font-semibold px-6 py-3 rounded-lg hover:bg-pink-600 focus:outline-none focus:ring-2 focus:ring-pink-400">
                    Update Employee
                </button>
                <button type="button" onclick="window.history.back()"
                        class="bg-pink-100 text-pink-500 font-semibold px-4 py-2 rounded-lg hover:bg-pink-200 focus:outline-none focus:ring-2 focus:ring-pink-400">
                    Cancel
                </button>
            </div>
        </form>
    </main>
</div>
</body>
</html>