<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Borrowings - Library Management System</title>
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
                <a href="browse-books"><i class="fas fa-book"></i> Browse Books</a>
                <a href="my-borrowings" class="active"><i class="fas fa-history"></i> My Borrowings</a>
                <a href="#"><i class="fas fa-user"></i> Profile</a>
            </nav>
        </aside>

        <main class="main-content">
            <header class="main-header">
                <div class="header-left">
                    <h2>My Borrowings</h2>
                </div>
                <div class="header-right">
                    <div class="user-profile">
                        <span>Welcome, ${username}</span>
                        <a href="logout" class="logout-btn"><i class="fas fa-sign-out-alt"></i></a>
                    </div>
                </div>
            </header>

            <div class="borrowing-container">
                <c:if test="${not empty error}">
                    <div class="alert alert-error">
                        <i class="fas fa-exclamation-circle"></i> ${error}
                    </div>
                </c:if>
                <c:if test="${not empty success}">
                    <div class="alert alert-success">
                        <i class="fas fa-check-circle"></i> ${success}
                    </div>
                </c:if>

                <div class="tabs">
                    <button class="tab-btn active" onclick="showTab('current')">Current</button>
                    <button class="tab-btn" onclick="showTab('overdue')">Overdue</button>
                    <button class="tab-btn" onclick="showTab('past')">History</button>
                </div>

                <div id="current" class="tab-content active">
                    <h3>Current Borrowings</h3>
                    <c:choose>
                        <c:when test="${not empty currentBorrowings}">
                            <div class="table-responsive">
                                <table>
                                    <thead>
                                        <tr>
                                            <th>Book Title</th>
                                            <th>Borrow Date</th>
                                            <th>Due Date</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${currentBorrowings}" var="tx">
                                            <tr>
                                                <td>${bookDAO.getBookById(tx.bookId).title}</td>
                                                <td><fmt:formatDate value="${tx.borrowDate}" pattern="yyyy-MM-dd"/></td>
                                                <td><fmt:formatDate value="${tx.dueDate}" pattern="yyyy-MM-dd"/></td>
                                                <td><span class="status ${tx.status}">${tx.status}</span></td>
                                                <td>
                                                    <c:if test="${tx.status eq 'approved'}">
                                                        <form action="return-book" method="post">
                                                            <input type="hidden" name="transactionId" value="${tx.transactionId}">
                                                            <button type="submit" class="return-btn">
                                                                <i class="fas fa-undo"></i> Return
                                                            </button>
                                                        </form>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="no-records">
                                <p>You have no current borrowings.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div id="overdue" class="tab-content">
                    <h3>Overdue Borrowings</h3>
                    <c:choose>
                        <c:when test="${not empty overdueBorrowings}">
                            <div class="table-responsive">
                                <table>
                                    <thead>
                                        <tr>
                                            <th>Book Title</th>
                                            <th>Borrow Date</th>
                                            <th>Due Date</th>
                                            <th>Days Overdue</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${overdueBorrowings}" var="tx">
                                            <tr>
                                                <td>${bookDAO.getBookById(tx.bookId).title}</td>
                                                <td><fmt:formatDate value="${tx.borrowDate}" pattern="yyyy-MM-dd"/></td>
                                                <td><fmt:formatDate value="${tx.dueDate}" pattern="yyyy-MM-dd"/></td>
                                                <td>
                                                    <jsp:useBean id="now" class="java.util.Date"/>
                                                    <fmt:formatNumber value="${(now.time - tx.dueDate.time) / (1000 * 60 * 60 * 24)}" maxFractionDigits="0"/>
                                                </td>
                                                <td>
                                                    <form action="return-book" method="post">
                                                        <input type="hidden" name="transactionId" value="${tx.transactionId}">
                                                        <button type="submit" class="return-btn">
                                                            <i class="fas fa-undo"></i> Return
                                                        </button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="no-records">
                                <p>You have no overdue borrowings.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div id="past" class="tab-content">
                    <h3>Past Borrowings</h3>
                    <c:choose>
                        <c:when test="${not empty pastBorrowings}">
                            <div class="table-responsive">
                                <table>
                                    <thead>
                                        <tr>
                                            <th>Book Title</th>
                                            <th>Borrow Date</th>
                                            <th>Return Date</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${pastBorrowings}" var="tx">
                                            <tr>
                                                <td>${bookDAO.getBookById(tx.bookId).title}</td>
                                                <td><fmt:formatDate value="${tx.borrowDate}" pattern="yyyy-MM-dd"/></td>
                                                <td><fmt:formatDate value="${tx.returnDate}" pattern="yyyy-MM-dd"/></td>
                                                <td><span class="status ${tx.status}">${tx.status}</span></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="no-records">
                                <p>You have no past borrowings.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </main>
    </div>

    <script>
        function showTab(tabId) {
            // Hide all tab contents
            document.querySelectorAll('.tab-content').forEach(tab => {
                tab.classList.remove('active');
            });
            
            // Deactivate all tab buttons
            document.querySelectorAll('.tab-btn').forEach(btn => {
                btn.classList.remove('active');
            });
            
            // Show the selected tab content
            document.getElementById(tabId).classList.add('active');
            
            // Activate the clicked tab button
            event.currentTarget.classList.add('active');
        }
    </script>
</body>
</html>