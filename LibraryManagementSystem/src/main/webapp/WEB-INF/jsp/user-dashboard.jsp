<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.library.model.Transaction" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Dashboard - Library Management System</title>
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
                <a href="#" class="active"><i class="fas fa-tachometer-alt"></i> Dashboard</a>
                <a href="browse-books"><i class="fas fa-book"></i> Browse Books</a>
                <a href="my-borrowings"><i class="fas fa-history"></i> My Borrowings</a>
                <a href="#"><i class="fas fa-user"></i> Profile</a>
            </nav>
        </aside>

        <main class="main-content">
            <header class="main-header">
                <div class="header-left">
                    <h2>User Dashboard</h2>
                </div>
                <div class="header-right">
                    <div class="user-profile">
                        <span>Welcome, <%= request.getAttribute("username") %></span>
                        <a href="logout" class="logout-btn"><i class="fas fa-sign-out-alt"></i></a>
                    </div>
                </div>
            </header>

            <div class="dashboard-cards">
		    <div class="card">
		        <div class="card-icon bg-primary">
		            <i class="fas fa-book"></i>
		        </div>
		        <div class="card-info">
		            <h3>Books Borrowed</h3>
		            <p>${borrowedCount}</p>
		        </div>
		    </div>
		    <div class="card">
		        <div class="card-icon bg-success">
		            <i class="fas fa-check-circle"></i>
		        </div>
		        <div class="card-info">
		            <h3>Available</h3>
		            <p>${availableBooks}</p>
		        </div>
		    </div>
		    <div class="card">
		        <div class="card-icon bg-warning">
		            <i class="fas fa-clock"></i>
		        </div>
		        <div class="card-info">
		            <h3>Pending</h3>
		            <p>${pendingCount}</p>
		        </div>
		    </div>
		    <div class="card">
		        <div class="card-icon bg-danger">
		            <i class="fas fa-exclamation-circle"></i>
		        </div>
		        <div class="card-info">
		            <h3>Overdue</h3>
		            <p>${overdueCount}</p>
		        </div>
		    </div>
		</div>
            <div class="dashboard-sections">
                <section class="current-borrowings">
                    <h3><i class="fas fa-bookmark"></i> Current Borrowings</h3>
                    <div class="table-responsive">
                        <table>
                            <thead>
                                <tr>
                                    <th>Book</th>
                                    <th>Borrow Date</th>
                                    <th>Due Date</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
						    <% 
						    List<Transaction> borrowings = (List<Transaction>) request.getAttribute("currentBorrowings");
						    if (borrowings != null) {
						        for (Transaction tx : borrowings) { 
						    %>
						    <tr>
						        <td><%= tx.getBookId() %></td>
						        <td><%= tx.getBorrowDate() %></td>
						        <td><%= tx.getDueDate() %></td>
						        <td><span class="status <%= tx.getStatus() %>"><%= tx.getStatus() %></span></td>
						    </tr>
						    <% 
						        } 
						    } 
						    %>
						</tbody>
                        </table>
                    </div>
                </section>

                <section class="book-search">
                    <h3><i class="fas fa-search"></i> Find a Book</h3>
                    <form class="search-form">
                        <div class="search-input">
                            <input type="text" placeholder="Search by title, author, or ISBN">
                            <button type="submit"><i class="fas fa-search"></i></button>
                        </div>
                    </form>
                </section>
            </div>
        </main>
    </div>
</body>
</html>