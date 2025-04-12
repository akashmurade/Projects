<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Library Management System</title>
    <link rel="stylesheet" href="css/styles.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&family=Playfair+Display:wght@400;700&display=swap" rel="stylesheet">
</head>
<body>
    <div class="landing-container">
        <div class="library-branding">
            <div class="logo">
                <i class="fas fa-book-open fa-3x"></i>
            </div>
            <h1>Library Management System</h1>
            <p class="subtitle">Manage and explore books with ease</p>
        </div>
        
        <div class="auth-options">
            <div class="auth-card">
                <h2>Existing User</h2>
                <a href="login" class="auth-btn login-btn">
                    <i class="fas fa-sign-in-alt"></i>
                    <span>Login</span>
                </a>
            </div>
            
            <div class="auth-card">
                <h2>New User</h2>
                <a href="register" class="auth-btn register-btn">
                    <i class="fas fa-user-plus"></i>
                    <span>Register</span>
                </a>
            </div>
        </div>
        
        <footer class="landing-footer">
            <p>&copy; 2023 Library Management System. All rights reserved.</p>
        </footer>
    </div>
</body>
</html>