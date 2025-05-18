<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html lang="en">
 <head>
  <meta charset="utf-8"/>
  <meta content="width=device-width, initial-scale=1" name="viewport"/>
  <title>
   ManageEase Project Overview
  </title>
  <script src="https://cdn.tailwindcss.com">
  </script>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&amp;display=swap" rel="stylesheet"/>
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
   <main class="flex-1 p-6 max-w-4xl mx-auto  ml-64">
    <div class="text-xs font-normal mb-6 text-black">
     <span>
      Home
     </span>
     <i class="fas fa-chevron-right mx-2 text-[10px]">
     </i>
     <span>
      Project Overview
     </span>
    </div>
    <h2 class="font-semibold text-sm mb-2 text-black">
     Project Summary
    </h2>
    <p class="text-xs font-normal text-gray-500 mb-6 max-w-xl">
     A full-stack web app for managing employees, leave, attendance,
        payroll, and logs with secure role-based access.
    </p>
    <h3 class="font-semibold text-xs mb-3 text-black">
     Key Features
    </h3>
    <div class="flex flex-wrap gap-4 mb-8">
     <div class="bg-[#f9e9e9] rounded-lg p-4 w-40 min-w-[160px] flex flex-col text-xs text-gray-500">
      <div class="mb-1 font-normal text-[9px] text-black">
       Employee
      </div>
      <div class="font-semibold text-black mb-1">
       Employee CRUD
      </div>
      <p class="leading-tight">
       Add, view, update, and remove employee records.
      </p>
     </div>
     <div class="bg-[#f9e9e9] rounded-lg p-4 w-40 min-w-[160px] flex flex-col text-xs text-gray-500">
      <div class="mb-1 font-normal text-[9px] text-black">
       Leave
      </div>
      <div class="font-semibold text-black mb-1">
       Leave Management
      </div>
      <p class="leading-tight">
       Submit, approve, and track leave requests in real time.
      </p>
     </div>
     <div class="bg-[#f9e9e9] rounded-lg p-4 w-40 min-w-[160px] flex flex-col text-xs text-gray-500">
      <div class="mb-1 font-normal text-[9px] text-black">
       Access
      </div>
      <div class="font-semibold text-black mb-1">
       Role Control
      </div>
      <p class="leading-tight">
       Secure, role-based access for Admin, HR, Manager, Employee.
      </p>
     </div>
     <div class="bg-[#f9e9e9] rounded-lg p-4 w-40 min-w-[160px] flex flex-col text-xs text-gray-500">
      <div class="mb-1 font-normal text-[9px] text-black">
       Auth
      </div>
      <div class="font-semibold text-black mb-1">
       Authentication
      </div>
      <p class="leading-tight">
       Login with hashed passwords and secure redirects.
      </p>
     </div>
    </div>
    <section class="mb-8 max-w-xl text-xs text-black">
     <h4 class="font-semibold mb-2">
      Project Goals
     </h4>
     <ul class="list-disc list-inside font-normal text-gray-700 space-y-1">
      <li>
       Robust Java backend
      </li>
      <li>
       Dynamic JSP frontend
      </li>
      <li>
       MySQL data integrity
      </li>
      <li>
       Secure RBAC
      </li>
      <li>
       User-friendly CRUD UI
      </li>
     </ul>
    </section>
    <h3 class="font-semibold text-xs mb-3 text-black">
     Functional Modules
    </h3>
    <div class="flex flex-wrap gap-4 mb-8">
     <div class="bg-[#f9e9e9] rounded-lg p-4 w-40 min-w-[160px] flex flex-col text-xs text-gray-500">
      <div class="font-semibold text-black mb-1">
       Employee Management
      </div>
      <p class="leading-tight">
       Full CRUD for employee records, job details, and leave entitlements.
      </p>
     </div>
     <div class="bg-[#f9e9e9] rounded-lg p-4 w-40 min-w-[160px] flex flex-col text-xs text-gray-500">
      <div class="font-semibold text-black mb-1">
       Leave Requests
      </div>
      <p class="leading-tight">
       Employees apply for leave; managers/HR approve or reject.
      </p>
     </div>
     <div class="bg-[#f9e9e9] rounded-lg p-4 w-40 min-w-[160px] flex flex-col text-xs text-gray-500">
      <div class="font-semibold text-black mb-1">
       Dashboards
      </div>
      <p class="leading-tight">
       Role-based dashboards with key metrics and quick links.
      </p>
     </div>
    </div>
    <section class="mb-8 max-w-xl text-xs text-black">
     <h4 class="font-semibold mb-2">
      Security &amp; Performance
     </h4>
     <ul class="list-disc list-inside font-normal text-gray-700 space-y-1">
      <li>
       Hashed passwords
      </li>
      <li>
       Input validation
      </li>
      <li>
       Prepared SQL statements
      </li>
      <li>
       Efficient queries
      </li>
      <li>
       Scalable to 100+ employees
      </li>
     </ul>
    </section>
    <section class="max-w-xl text-xs text-black">
     <h4 class="font-semibold mb-2">
      Bonus Features
     </h4>
     <ul class="list-disc list-inside font-normal text-gray-700 space-y-1">
      <li>
       Pagination for lists
      </li>
      <li>
       Search and filter
      </li>
      <li>
       PDF report generation
      </li>
     </ul>
    </section>
   </main>
  </div>
 </body>
</html>
