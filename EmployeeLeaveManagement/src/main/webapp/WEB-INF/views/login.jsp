<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Sign In</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
</head>
<body class="bg-white h-screen flex flex-col">

  <!-- Header -->
  <header class="border-b border-gray-200">
    <nav class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 flex items-center justify-between h-16">
      <div class="font-semibold text-base">ManageEase</div>
    </nav>
  </header>

  <!-- Main Content -->
  <main class="flex-grow flex items-center justify-center">
    <form class="w-full max-w-xs" method="post" action="login">
      <h2 class="text-center font-semibold text-lg text-black mb-1">Sign In</h2>

      <c:if test="${not empty error}">
        <p class="text-red-500 text-sm text-center mb-4">${error}</p>
      </c:if>

      <label for="username" class="block mb-2 text-black font-semibold text-xs">Username</label>
      <div class="relative mb-4">
        <input
          id="username"
          name="username"
          type="text"
          placeholder="Enter your username"
          class="w-full pl-3 pr-9 py-2 text-sm text-black bg-[#f9fbf8] border border-transparent rounded"
        />
        <i class="fas fa-user absolute right-3 top-1/2 -translate-y-1/2 text-black text-sm"></i>
      </div>

      <label for="password" class="block mb-2 text-black font-semibold text-xs">Password</label>
      <div class="relative mb-6">
        <input
          id="password"
          name="password"
          type="password"
          placeholder="Enter your password"
          class="w-full pl-3 pr-9 py-2 text-sm text-black bg-[#f9fbf8] border border-transparent rounded"
        />
        <i class="fas fa-lock absolute right-3 top-1/2 -translate-y-1/2 text-black text-sm"></i>
      </div>

      <div class="text-center">
        <button
          type="submit"
          class="bg-pink-500 hover:bg-pink-600 text-white text-sm font-semibold rounded-md px-4 py-2"
        >
          Login
        </button>
      </div>
    </form>
  </main>

</body>
</html>
