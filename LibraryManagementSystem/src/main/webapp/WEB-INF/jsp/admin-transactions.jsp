<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="admin-section">
    <div class="section-header">
        <h3><i class="fas fa-exchange-alt"></i> All Transactions</h3>
        <div class="filter-options">
            <select id="statusFilter" onchange="filterTransactions()">
                <option value="">All Statuses</option>
                <option value="pending">Pending</option>
                <option value="approved">Approved</option>
                <option value="rejected">Rejected</option>
                <option value="returned">Returned</option>
            </select>
        </div>
    </div>

    <div class="table-responsive">
        <table id="transactionsTable">
            <thead>
                <tr>
                    <th>User</th>
                    <th>Book</th>
                    <th>Borrow Date</th>
                    <th>Due Date</th>
                    <th>Return Date</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${transactions}" var="tx">
                    <tr data-status="${tx.status}">
                        <td>${tx.userId}</td>
                        <td>${tx.bookId}</td>
                        <td><fmt:formatDate value="${tx.borrowDate}" pattern="yyyy-MM-dd" /></td>
                        <td><fmt:formatDate value="${tx.dueDate}" pattern="yyyy-MM-dd" /></td>
                        <td>
                            <c:if test="${tx.returnDate != null}">
                                <fmt:formatDate value="${tx.returnDate}" pattern="yyyy-MM-dd" />
                            </c:if>
                        </td>
                        <td><span class="status ${tx.status}">${tx.status}</span></td>
                        <td>
                            <c:if test="${tx.status == 'pending'}">
                                <form method="post" action="admin-dashboard" style="display:inline;">
                                    <input type="hidden" name="action" value="approve">
                                    <input type="hidden" name="transactionId" value="${tx.transactionId}">
                                    <input type="hidden" name="redirectTo" value="view-transactions">
                                    <button type="submit" class="action-btn approve">Approve</button>
                                </form>
                                <form method="post" action="admin-dashboard" style="display:inline;">
                                    <input type="hidden" name="action" value="reject">
                                    <input type="hidden" name="transactionId" value="${tx.transactionId}">
                                    <input type="hidden" name="redirectTo" value="view-transactions">
                                    <button type="submit" class="action-btn reject">Reject</button>
                                </form>
                            </c:if>
                            <c:if test="${tx.status == 'approved'}">
                                <form method="post" action="admin-dashboard" style="display:inline;">
                                    <input type="hidden" name="action" value="return">
                                    <input type="hidden" name="transactionId" value="${tx.transactionId}">
                                    <input type="hidden" name="redirectTo" value="view-transactions">
                                    <button type="submit" class="action-btn">Return</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script>
        function filterTransactions() {
            const status = document.getElementById('statusFilter').value;
            const rows = document.querySelectorAll('#transactionsTable tbody tr');
            
            rows.forEach(row => {
                if (status === '' || row.getAttribute('data-status') === status) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        }
    </script>
</div>