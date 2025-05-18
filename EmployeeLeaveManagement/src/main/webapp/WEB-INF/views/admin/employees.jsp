<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
 <head>
  <meta charset="utf-8"/>
  <meta content="width=device-width, initial-scale=1" name="viewport"/>
  <title>
   Employee Directory
  </title>
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
   
   <!-- Sidebar -->
   <jsp:include page="sidebar.jsp" />
   
   <!-- Main content -->
   <main class="flex-1 p-6 max-w-4xl mx-auto ml-64"> <!-- Added margin-left for alignment -->
    <nav class="flex items-center gap-1 text-sm text-black mb-4 select-none">
     <a class="hover:underline" href="#">Home</a>
     <i class="fas fa-chevron-right text-xs text-gray-600"></i>
     <span>Employees</span>
    </nav>
    <h2 class="font-semibold text-sm mb-2 text-black">Employee Directory</h2>
    <p class="text-xs font-normal text-gray-500 mb-6 max-w-xl">
     View, search, and manage all employee records. Use filters or add a new employee.
    </p>

	
	<div class="flex flex-col sm:flex-row sm:items-center sm:justify-between mb-6 gap-4">
  <!-- Search form -->
  <form action="${pageContext.request.contextPath}/admin/employees/search" method="GET" class="relative flex-1 max-w-xl w-full mb-0">
    <input 
      class="w-full bg-gray-50 border border-gray-200 rounded-md py-3 px-4 pr-[6.5rem] text-sm placeholder:text-gray-600 focus:outline-none focus:ring-2 focus:ring-pink-400 focus:border-pink-400" 
      name="query" 
      id="search" 
      placeholder="Search by name, ID, or department" 
      type="search"
    />
    <button 
      type="submit" 
      class="absolute right-2 top-1/2 -translate-y-1/2 bg-pink-700 hover:bg-pink-800 text-white font-medium rounded-md text-sm px-4 py-2 dark:bg-pink-600 dark:hover:bg-pink-700 dark:focus:ring-pink-800"
    >
      Search
    </button>
  </form>

  <!-- Add Employee button -->
  <a href="${pageContext.request.contextPath}/admin/employees/add">
    <button 
      class="bg-pink-500 hover:bg-pink-600 text-white font-semibold rounded-md px-5 py-3 text-sm whitespace-nowrap" 
      type="button"
    >
      Add Employee
    </button>
  </a>
</div>

    <h3 class="font-semibold text-xs mb-3 text-black">Employee List</h3>
    
	<ul class="divide-y divide-gray-200 border-t border-gray-200">
	    <c:forEach var="emp" items="${employeeList}">
	        <li class="flex items-center gap-4 py-3 text-sm select-none">
	            <i class="far fa-id-badge text-lg text-black/80"></i>
	
	            <span class="w-16">E${emp.employeeId}</span>
	            <span class="flex-1 max-w-[160px]">${emp.firstName} ${emp.lastName}</span>
	            <span class="w-36">${emp.role}</span>
	            <span class="w-20">${emp.department}</span>
	            <span class="w-20">${emp.employeeStatus}</span>
	
	            <!-- Button Container -->
	            <div class="flex items-center gap-2">
	                <!-- Edit Button -->
	                <form action="${pageContext.request.contextPath}/admin/employees/update" class="m-0 p-0">
	                    <input type="hidden" name="employeeId" value="${emp.employeeId}">
		                <button class="text-black hover:text-pink-600">
		                    <i class="fas fa-edit"></i>
		                </button>
	                </form>
	
	                <!-- Delete Button Form -->
	                <form action="${pageContext.request.contextPath}/admin/employees/delete" method="POST" class="m-0 p-0">
	                    <input type="hidden" name="employeeId" value="${emp.employeeId}">
	                    <button type="submit" onclick="return confirmDelete()" class="text-black hover:text-pink-600 ml-4">
	                        <i class="fas fa-trash-alt"></i>
	                    </button>
	                </form>
	            </div>
	        </li>
	    </c:forEach>
	</ul>


   </main>
  </div>
  	<script>
	  function confirmDelete() {
	    if (confirm("Are you sure you want to delete this employee?")) {
	      // Submit the form if confirmed
	      document.getElementById('deleteForm').submit();
	    }
	  }
	</script>
 </body>
</html>
