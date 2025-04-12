<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Library Management System</title>
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
            <h1>Create Account</h1>
            <p class="subtitle">Join our library community</p>
        </div>
        
        <form class="auth-form" action="register" method="post">
	        <%
	            String errorMessage = (String) request.getAttribute("errorMessage");
	            if (errorMessage != null) {
	        %>
	            <div class="error-message">
	                <i class="fas fa-exclamation-circle"></i>
	                <span><%= errorMessage %></span>
	            </div>
	        <%
	            }
	        %>
            <div class="form-group">
                <label for="fullname">Full Name</label>
                <div class="input-wrapper">
                    <i class="fas fa-user input-icon"></i>
                    <input type="text" id="fullname" name="fullname" placeholder="Enter your full name" required>
                </div>
            </div>
            
            <div class="form-group">
                <label for="email">Email</label>
                <div class="input-wrapper">
                    <i class="fas fa-envelope input-icon"></i>
                    <input type="email" id="email" name="email" placeholder="Enter your email" required>
                </div>
            </div>
            
            <div class="form-group">
                <label for="username">Username</label>
                <div class="input-wrapper">
                    <i class="fas fa-user-tag input-icon"></i>
                    <input type="text" id="username" name="username" placeholder="Choose a username" required>
                </div>
            </div>
            
            <div class="form-group">
                <label for="password">Password</label>
                <div class="input-wrapper">
                    <i class="fas fa-lock input-icon"></i>
                    <input type="password" id="password" name="password" placeholder="Create a password" required>
                </div>
            </div>
            
            <div class="form-group">
                <label for="confirm-password">Confirm Password</label>
                <div class="input-wrapper">
                    <i class="fas fa-lock input-icon"></i>
                    <input type="password" id="confirm-password" name="confirm-password" placeholder="Confirm your password" required>
                </div>
            </div>
            
            <button type="submit" class="auth-btn register-btn">
                <i class="fas fa-user-plus"></i>
                <span>Register</span>
            </button>
            
            <div class="auth-footer">
                <p>Already have an account? <a href="login" class="auth-link">Login here</a></p>
            </div>
        </form>
    </div>
</body>
</html>