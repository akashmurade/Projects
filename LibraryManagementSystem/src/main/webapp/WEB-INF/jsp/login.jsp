<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Library Management System</title>
    <link rel="stylesheet" href="css/styles.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&family=Playfair+Display:wght@400;700&display=swap" rel="stylesheet">
</head>
<body>
    <div class="auth-container">
        <div class="auth-header">
            <div class="logo">
                <i class="fas fa-book-open fa-3x"></i>
            </div>
            <h1>Welcome Back</h1>
            <p class="subtitle">Login to access your account</p>
        </div>
		        
        <form class="auth-form" action="login" method="post">
        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
		<% if (errorMessage != null) { %>
		    <div class="error-message">
		        <i class="fas fa-exclamation-circle"></i>
		        <%= errorMessage %>
		    </div>
		<% } %>
            <div class="form-group">
                <label for="username">Username</label>
                <div class="input-wrapper">
                    <i class="fas fa-user input-icon"></i>
                    <input type="text" id="username" name="username" placeholder="Enter your username" required>
                </div>
            </div>
            
            <div class="form-group">
                <label for="password">Password</label>
                <div class="input-wrapper">
                    <i class="fas fa-lock input-icon"></i>
                    <input type="password" id="password" name="password" placeholder="Enter your password" required>
                </div>
            </div>
            
            <div class="form-options">
                <label class="remember-me">
                    <input type="checkbox" name="remember"> Remember me
                </label>
                <a href="#" class="forgot-password">Forgot password?</a>
            </div>
            
            <button type="submit" class="auth-btn login-btn">
                <i class="fas fa-sign-in-alt"></i>
                <span>Login</span>
            </button>
            
            <div class="auth-footer">
                <p>Don't have an account? <a href="register" class="auth-link">Register here</a></p>
            </div>
        </form>
    </div>
</body>
</html>