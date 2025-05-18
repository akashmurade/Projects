<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta content="width=device-width, initial-scale=1" name="viewport" />
    <title>Employee Dashboard - OrgManager</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap" rel="stylesheet" />
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
        <div class="text-xs font-normal mb-6 text-black">
            <span>Home</span>
            <i class="fas fa-chevron-right mx-2 text-[10px]"></i>
            <span>Dashboard</span>
        </div>

        <h2 class="font-semibold text-sm mb-2 text-black">Welcome</h2>
        <p class="text-xs font-normal text-gray-500 mb-6 max-w-xl">Your quick overview</p>

        <h3 class="font-semibold text-xs mb-3 text-black">Quick Actions</h3>
        <div class="flex flex-wrap gap-4 mb-8">
            <!-- Apply Leave -->
            <div class="bg-[#f9e9e9] rounded-lg p-4 w-48 min-w-[160px] flex flex-col text-xs text-gray-500">
                <div class="mb-2 text-pink-600 text-lg">
                    <i class="fas fa-calendar-plus"></i>
                </div>
                <div class="font-semibold text-black mb-1">Apply Leave</div>
                <p class="leading-tight mb-3">Request time off easily.</p>
                <a href="applyLeave.jsp" class="text-white bg-pink-500 hover:bg-pink-600 text-center rounded py-1 text-xs">New Leave</a>
            </div>

            <!-- View Attendance -->
            <div class="bg-[#f9e9e9] rounded-lg p-4 w-48 min-w-[160px] flex flex-col text-xs text-gray-500">
                <div class="mb-2 text-pink-600 text-lg">
                    <i class="fas fa-clock"></i>
                </div>
                <div class="font-semibold text-black mb-1">View Attendance</div>
                <p class="leading-tight mb-3">Check your attendance records.</p>
                <a href="attendance.jsp" class="text-white bg-pink-500 hover:bg-pink-600 text-center rounded py-1 text-xs">View</a>
            </div>

            <!-- Payslips -->
            <div class="bg-[#f9e9e9] rounded-lg p-4 w-48 min-w-[160px] flex flex-col text-xs text-gray-500">
                <div class="mb-2 text-pink-600 text-lg">
                    <i class="fas fa-file-invoice-dollar"></i>
                </div>
                <div class="font-semibold text-black mb-1">Payslips</div>
                <p class="leading-tight mb-3">Download your latest payslips.</p>
                <a href="payslip.jsp" class="text-white bg-pink-500 hover:bg-pink-600 text-center rounded py-1 text-xs">Download</a>
            </div>
        </div>
    </main>
</div>
</body>
</html>
