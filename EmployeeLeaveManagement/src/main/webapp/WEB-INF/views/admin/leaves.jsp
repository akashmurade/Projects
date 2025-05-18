<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
  <meta charset="utf-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Leave Management</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
  <style>
    body {
      font-family: 'Inter', sans-serif;
    }
  </style>
</head>
<body class="bg-white text-black">
  <div class="flex min-h-screen">
    <jsp:include page="sidebar.jsp" />

    <!-- Main content -->
    <main class="flex-1 p-6 ml-64 max-w-5xl">
      <div class="text-xs text-black mb-6">
        <span>Home</span>
        <i class="fas fa-chevron-right mx-2 text-[10px]"></i>
        <span>Leaves</span>
      </div>

      <h2 class="text-lg font-semibold text-black mb-1">Leave Management</h2>
      <p class="text-xs text-gray-600 mb-6">View, approve, and track employee leave requests in real time.</p>

     	 <!-- Tabs -->
      	<div class="flex space-x-6 text-sm border-b border-gray-200">
		  <a href="${pageContext.request.contextPath}/admin/leaves" class="${empty param.status ? 'pb-2 font-semibold border-b-2 border-black' : 'pb-2 text-gray-500 hover:text-black'}">All Requests</a>
		  <a href="${pageContext.request.contextPath}/admin/leaves?status=pending" class="${param.status.equalsIgnoreCase('pending') ? 'pb-2 font-semibold border-b-2 border-black' : 'pb-2 text-gray-500 hover:text-black'}">Pending</a>
		  <a href="${pageContext.request.contextPath}/admin/leaves?status=approved" class="${param.status.equalsIgnoreCase('approved') ? 'pb-2 font-semibold border-b-2 border-black' : 'pb-2 text-gray-500 hover:text-black'}">Approved</a>
		  <a href="${pageContext.request.contextPath}/admin/leaves?status=rejected" class="${param.status.equalsIgnoreCase('rejected') ? 'pb-2 font-semibold border-b-2 border-black' : 'pb-2 text-gray-500 hover:text-black'}">Rejected</a>
		</div>

		<table class="w-full text-xs text-left border-t border-gray-200">
		    <thead>
		        <tr class="text-gray-500">
		            <th class="py-2">Name</th>
		            <th class="py-2">Type</th>
		            <th class="py-2">Dates</th>
		            <th class="py-2">Days</th>
		            <th class="py-2">Status</th>
		            <th class="py-2 text-center">Actions</th>
		        </tr>
		    </thead>
		    <tbody class="text-black">
		        <c:forEach var="leave" items="${leaveList}">
		            <tr class="border-t border-gray-100">
		                <td class="py-2">${leave.employeeName}</td>
		                <td class="py-2">${leave.leaveType}</td>
		                <td class="py-2">
		                    ${leave.startDate == leave.endDate ? leave.startDate : leave.startDate + " to " + leave.endDate}
		                </td>
		                <td class="py-2">${leave.leaveDays} days</td>
		                <td class="py-2">
		                    <span class="font-medium 
		                        ${leave.leaveStatus == 'Pending' ? 'text-yellow-600' : 
		                          leave.leaveStatus == 'Approved' ? 'text-green-600' : 
		                          'text-red-600'}">
		                        ${leave.leaveStatus}
		                    </span>
		                </td>
		                <td class="py-2 text-center">
		                    <c:choose>
		                        <c:when test="${leave.leaveStatus == 'Pending'}">
		                        	<form action="${pageContext.request.contextPath}/admin/leaves" method="post">
		                        		<input type="hidden" name="leaveId" value="${leave.leaveId }" />
		                            	<button type="submit" name="action" value="Approved" class="text-green-600 hover:text-green-800 mr-2"><i class="fas fa-check"></i></button>
			                            <button type="submit" name="action" value="Rejected" class="text-red-600 hover:text-red-800"><i class="fas fa-times"></i></button>
		                            </form>
		                        </c:when>
		                        <c:otherwise>
		                            <button class="text-gray-600 hover:text-black"><i class="fas fa-eye"></i></button>
		                        </c:otherwise>
		                    </c:choose>
		                </td>
		            </tr>
		        </c:forEach>
		    </tbody>
		</table>

    </main>
  </div>
</body>
</html>
