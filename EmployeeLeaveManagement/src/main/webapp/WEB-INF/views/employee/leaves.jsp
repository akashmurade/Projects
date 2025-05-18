<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Leave Management - OrgManager</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Inter', sans-serif;
        }
    </style>
</head>
<body class="bg-white text-black">
<div class="flex min-h-screen">

    <!-- Sidebar -->
    <jsp:include page="sidebar.jsp" />

    <!-- Main Content -->
    <main class="flex-1 p-6 max-w-6xl mx-auto ml-64">
        <!-- Breadcrumb -->
        <div class="text-xs font-normal mb-6 text-black">
            <span>Dashboard</span>
            <i class="fas fa-chevron-right mx-2 text-[10px]"></i>
            <span>Leaves</span>
        </div>

        <!-- Page Title -->
        <h2 class="font-semibold text-sm mb-2 text-black">Leave Overview</h2>
        <p class="text-xs text-gray-500 mb-6 max-w-xl">Track your leave balance, request new leaves, and view leave history.</p>

        <!-- Leave Balance Cards -->
        <div class="flex gap-4 mb-8">
		    <div class="bg-pink-50 p-4 rounded-xl w-1/3 text-xs text-gray-600">
		        <div class="text-pink-600 text-lg mb-1"><i class="fas fa-umbrella-beach"></i></div>
		        <div class="text-black font-bold text-sm mb-1">
		            ${leaveBalance} days left
		        </div>
		        <div class="text-xs">Total Leaves Left</div>
		    </div>
		</div>


        <!-- Request Leave Form -->
        <form action="${pageContext.request.contextPath }/employee/leave/apply" method="post" class="mb-10">
        <c:if test="${not empty error}">
	        <div class="bg-red-500 text-white p-4 mb-4 rounded">
	            <strong>Error: </strong>${error}
	        </div>
	    </c:if>
            <h3 class="font-semibold text-xs mb-3 text-black">Request Leave</h3>
            <div class="bg-gray-50 p-4 rounded-lg text-xs space-y-4">
            	<input type="hidden" name="employeeId" value="${sessionScope.employeeId}" />
                <div>
                    <label class="block mb-1">Leave Type</label>
                    <select name="leaveType" required class="w-full border px-2 py-1 rounded text-sm">
                        <option value="">Select leave type</option>
                        <option value="Annual">Annual</option>
                        <option value="Sick">Sick</option>
                        <option value="Casual">Casual</option>
                    </select>
                </div>
                <div>
                    <label class="block mb-1">From Date</label>
                    <input type="date" name="fromDate" required class="w-full border px-2 py-1 rounded text-sm" />
                </div>
                <div>
                    <label class="block mb-1">To Date</label>
                    <input type="date" name="toDate" required class="w-full border px-2 py-1 rounded text-sm" />
                </div>
                <div>
                    <label class="block mb-1">Reason</label>
                    <textarea name="reason" rows="3" class="w-full border px-2 py-1 rounded text-sm" placeholder="Enter reason"></textarea>
                </div>
                <div>
                    <button type="submit" class="bg-pink-500 hover:bg-pink-600 text-white px-4 py-1 rounded text-xs">
                        Submit Request
                    </button>
                </div>
            </div>
        </form>

        <!-- Leave History Table -->
		<h3 class="font-semibold text-xs mb-3 text-black">Leave History</h3>
		<table class="w-full text-xs text-left border-t border-gray-200">
		    <thead>
		        <tr class="text-gray-500">
		            <th class="py-2">Leave ID</th>
		            <th class="py-2">Type</th>
		            <th class="py-2">Dates</th>
		            <th class="py-2">Status</th>
		        </tr>
		    </thead>
		    <tbody class="text-black">
		        <!-- Loop through leaveHistory and display each leave request -->
		        <c:forEach var="leave" items="${leaveHistory}">
		            <tr class="border-t border-gray-100">
		                <td class="py-2">${leave.leaveId}</td>
		                <td class="py-2">${leave.leaveType}</td>
		                <td class="py-2">${leave.startDate == leave.endDate ? leave.startDate : leave.startDate + " to " + leave.endDate}</td>
		                <td class="py-2">${leave.leaveStatus}</td>
		            </tr>
		        </c:forEach>
		    </tbody>
		</table>
    </main>
</div>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const fromDate = document.querySelector("input[name='fromDate']");
        const toDate = document.querySelector("input[name='toDate']");

        fromDate.addEventListener("change", function () {
            toDate.min = this.value;
            if (toDate.value < this.value) {
                toDate.value = this.value;
            }
        });
    });
</script>

</body>
</html>
