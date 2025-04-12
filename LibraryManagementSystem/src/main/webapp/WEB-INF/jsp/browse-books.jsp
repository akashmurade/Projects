<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.library.model.Book" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Browse Books - Library Management System</title>
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&family=Playfair+Display:wght@400;700&display=swap" rel="stylesheet">
</head>
<body>
    <div class="dashboard-container">
        <aside class="sidebar">
            <div class="sidebar-header">
                <div class="logo">
                    <i class="fas fa-book-open fa-2x"></i>
                </div>
                <h3>Library User</h3>
            </div>
            <nav class="sidebar-nav">
                <a href="user-dashboard"><i class="fas fa-tachometer-alt"></i> Dashboard</a>
                <a href="browse-books" class="active"><i class="fas fa-book"></i> Browse Books</a>
                <a href="my-borrowings"><i class="fas fa-history"></i> My Borrowings</a>
                <a href="#"><i class="fas fa-user"></i> Profile</a>
            </nav>
        </aside>

        <main class="main-content">
            <header class="main-header">
                <div class="header-left">
                    <h2>Browse Books</h2>
                </div>
                <div class="header-right">
                    <div class="user-profile">
                        <span>Welcome, <%= request.getAttribute("username") %></span>
                        <a href="logout" class="logout-btn"><i class="fas fa-sign-out-alt"></i></a>
                    </div>
                </div>
            </header>

            <div class="book-browsing">
                <form class="search-form" method="get" action="browse-books">
                    <div class="search-input">
                        <input type="text" name="query" placeholder="Search by title, author, or ISBN" 
                               value="<%= request.getParameter("query") != null ? request.getParameter("query") : "" %>">
                        <button type="submit"><i class="fas fa-search"></i></button>
                    </div>
                    <div class="filter-options">
                        <select name="category">
                            <option value="">All Categories</option>
                            <option value="Fiction" <%= "Fiction".equals(request.getParameter("category")) ? "selected" : "" %>>Fiction</option>
                            <option value="Non-Fiction" <%= "Non-Fiction".equals(request.getParameter("category")) ? "selected" : "" %>>Non-Fiction</option>
                            <option value="Science" <%= "Science".equals(request.getParameter("category")) ? "selected" : "" %>>Science</option>
                            <option value="Technology" <%= "Technology".equals(request.getParameter("category")) ? "selected" : "" %>>Technology</option>
                            <option value="Literature" <%= "Literature".equals(request.getParameter("category")) ? "selected" : "" %>>Literature</option>
                        </select>
                        <button type="submit" class="filter-btn">Filter</button>
                    </div>
                </form>

                <div class="books-grid">
                    <% 
                    List<Book> books = (List<Book>) request.getAttribute("books");
                    if (books != null && !books.isEmpty()) {
                        for (Book book : books) { 
                    %>
                    <div class="book-card">
                        <div class="book-cover">
                            <i class="fas fa-book fa-3x"></i>
                        </div>
                        <div class="book-info">
                            <h3><%= book.getTitle() %></h3>
                            <p class="author"><%= book.getAuthor() %></p>
                            <p class="isbn">ISBN: <%= book.getIsbn() %></p>
                            <p class="category"><%= book.getCategory() %></p>
                            <div class="availability">
                                <span class="copies"><%= book.getAvailableCopies() %> of <%= book.getTotalCopies() %> available</span>
                                <% if (book.getAvailableCopies() > 0) { %>
                                    <form method="post" action="borrow-book">
                                        <input type="hidden" name="bookId" value="<%= book.getBookId() %>">
                                        <button type="submit" class="borrow-btn">Borrow</button>
                                    </form>
                                <% } else { %>
                                    <button class="borrow-btn disabled" disabled>Not Available</button>
                                <% } %>
                            </div>
                        </div>
                    </div>
                    <% 
                        } 
                    } else { 
                    %>
                    <div class="no-books">
                        <p>No books found matching your criteria.</p>
                    </div>
                    <% } %>
                </div>
            </div>
        </main>
    </div>
</body>
</html>