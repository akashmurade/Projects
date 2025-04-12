<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Library Management System</title>
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
                <h3>Library Admin</h3>
            </div>
            <c:set var="currentAction" value="${param.action}" />
			<c:if test="${empty currentAction}">
			    <c:set var="currentAction" value="dashboard" />
			</c:if>
			
			<nav class="sidebar-nav">
			    <a href="admin-dashboard" class="${currentAction == 'dashboard' ? 'active' : ''}">
			        <i class="fas fa-tachometer-alt"></i> Dashboard
			    </a>
			    <a href="admin-dashboard?action=manage-books" class="${currentAction == 'manage-books' ? 'active' : ''}">
			        <i class="fas fa-book"></i> Manage Books
			    </a>
			    <a href="admin-dashboard?action=manage-users" class="${currentAction == 'manage-users' ? 'active' : ''}">
			        <i class="fas fa-users"></i> Manage Users
			    </a>
			    <a href="admin-dashboard?action=view-transactions" class="${currentAction == 'view-transactions' ? 'active' : ''}">
			        <i class="fas fa-exchange-alt"></i> View Transactions
			    </a>
			</nav>

        </aside>

        <main class="main-content">
            <header class="main-header">
                <div class="header-left">
                    <h2>Admin Dashboard</h2>
                </div>
                <div class="header-right">
                    <div class="user-profile">
                        <span>Welcome, ${username}</span>
                        <a href="logout" class="logout-btn"><i class="fas fa-sign-out-alt"></i></a>
                    </div>
                </div>
            </header>

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

            <c:choose>
                <c:when test="${param.action == 'manage-books'}">
                    <jsp:include page="admin-books.jsp" flush="true" />
                </c:when>
                <c:when test="${param.action == 'manage-users'}">
                    <jsp:include page="admin-users.jsp" flush="true" />
                </c:when>
                <c:when test="${param.action == 'view-transactions'}">
                    <jsp:include page="admin-transactions.jsp" flush="true" />
                </c:when>
                <c:otherwise>
                    <div class="dashboard-cards">
                        <div class="card">
                            <div class="card-icon bg-primary">
                                <i class="fas fa-book"></i>
                            </div>
                            <div class="card-info">
                                <h3>Total Books</h3>
                                <p>${totalBooks}</p>
                            </div>
                        </div>
                        <div class="card">
                            <div class="card-icon bg-success">
                                <i class="fas fa-check-circle"></i>
                            </div>
                            <div class="card-info">
                                <h3>Available Books</h3>
                                <p>${availableBooks}</p>
                            </div>
                        </div>
                        <div class="card">
                            <div class="card-icon bg-warning">
                                <i class="fas fa-clock"></i>
                            </div>
                            <div class="card-info">
                                <h3>Pending Requests</h3>
                                <p>${pendingRequests}</p>
                            </div>
                        </div>
                        <div class="card">
                            <div class="card-icon bg-danger">
                                <i class="fas fa-exclamation-circle"></i>
                            </div>
                            <div class="card-info">
                                <h3>Overdue Books</h3>
                                <p>${overdueCount}</p>
                            </div>
                        </div>
                    </div>

                    <div class="dashboard-sections">
                        <section class="recent-transactions">
                            <h3><i class="fas fa-exchange-alt"></i> Recent Transactions</h3>
                            <div class="table-responsive">
                                <table>
                                    <thead>
                                        <tr>
                                            <th>User</th>
                                            <th>Book</th>
                                            <th>Borrow Date</th>
                                            <th>Due Date</th>
                                            <th>Status</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${transactions}" var="tx">
                                            <tr>
                                                <td>${tx.userId}</td>
                                                <td>${tx.bookId}</td>
                                                <td><fmt:formatDate value="${tx.borrowDate}" pattern="yyyy-MM-dd" /></td>
                                                <td><fmt:formatDate value="${tx.dueDate}" pattern="yyyy-MM-dd" /></td>
                                                <td><span class="status ${tx.status}">${tx.status}</span></td>
                                                <td>
                                                    <c:if test="${tx.status == 'pending'}">
                                                        <form method="post" action="admin-dashboard" style="display:inline;">
                                                            <input type="hidden" name="action" value="approve">
                                                            <input type="hidden" name="transactionId" value="${tx.transactionId}">
                                                            <input type="hidden" name="redirectTo" value="">
                                                            <button type="submit" class="action-btn approve">Approve</button>
                                                        </form>
                                                        <form method="post" action="admin-dashboard" style="display:inline;">
                                                            <input type="hidden" name="action" value="reject">
                                                            <input type="hidden" name="transactionId" value="${tx.transactionId}">
                                                            <input type="hidden" name="redirectTo" value="">
                                                            <button type="submit" class="action-btn reject">Reject</button>
                                                        </form>
                                                    </c:if>
                                                    <c:if test="${tx.status == 'approved'}">
                                                        <form method="post" action="admin-dashboard" style="display:inline;">
                                                            <input type="hidden" name="action" value="return">
                                                            <input type="hidden" name="transactionId" value="${tx.transactionId}">
                                                            <input type="hidden" name="redirectTo" value="">
                                                            <button type="submit" class="action-btn">Return</button>
                                                        </form>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="view-all">
                                <a href="admin-dashboard?action=view-transactions">View All Transactions</a>
                            </div>
                        </section>

                        <section class="recent-users">
                            <h3><i class="fas fa-users"></i> Recent Users</h3>
                            <div class="table-responsive">
                                <table>
                                    <thead>
                                        <tr>
                                            <th>Username</th>
                                            <th>Name</th>
                                            <th>Email</th>
                                            <th>Role</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${recentUsers}" var="user">
                                            <tr>
                                                <td>${user.username}</td>
                                                <td>${user.name}</td>
                                                <td>${user.email}</td>
                                                <td>${user.role}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="view-all">
                                <a href="admin-dashboard?action=manage-users">View All Users</a>
                            </div>
                        </section>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>
    </div>
</body>
</html>