<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Sidebar -->
   <aside class="fixed top-0 left-0 h-full flex flex-col justify-between bg-[#f9f4f4] w-64 p-6">
    <div>
     <h1 class="font-semibold text-sm mb-6">
      ManageEase
     </h1>
     <nav class="space-y-1 text-xs font-normal text-black">
     	<a class="block rounded-md px-3 py-1.5 font-normal text-black 
		                ${pageContext.request.requestURI.contains('/dashboard') ? 'bg-[#f9e9e9]' : ''}" 
		   href="<c:url value='/employee/dashboard' />">
		    Dashboard
		</a>
		<a class="block px-3 py-1.5 
		                ${pageContext.request.requestURI.contains('/leaves') ? 'bg-[#f9e9e9]' : ''}" 
		   href="<c:url value='/employee/leaves' />">
		    Leaves
		</a>
		<a class="block px-3 py-1.5 
		                ${pageContext.request.requestURI.contains('/attendance') ? 'bg-[#f9e9e9]' : ''}" 
		   href="<c:url value='/employee/attendance' />">
		    Attendance
		</a>
		<a class="block px-3 py-1.5 
		                ${pageContext.request.requestURI.contains('/payslips') ? 'bg-[#f9e9e9]' : ''}" 
		   href="<c:url value='/employee/payslips' />">
		    Payslips
		</a>
     </nav>
    </div>
    <div class="flex justify-between items-center text-xs text-black px-4 py-2 bg-white">
	  <!-- User Info -->
	  <div class="flex items-center space-x-3 leading-tight">
	    <div>
	      <div class="font-semibold">
	        ${sessionScope.username}
	      </div>
	      <div class="text-[9px] font-normal">
	        ${sessionScope.role}
	      </div>
	    </div>
	  </div>
	
	  <a href="${pageContext.request.contextPath }/logout" class="text-red-600 hover:underline text-xs font-semibold leading-none">
	      Logout
	  </a>
	</div>
   </aside>