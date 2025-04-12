<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="admin-section">
    <div class="section-header">
        <h3><i class="fas fa-users"></i> Manage Users</h3>
    </div>

    <div class="table-responsive">
        <table>
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.username}</td>
                        <td>${user.name}</td>
                        <td>${user.email}</td>
                        <td>${user.role}</td>
                        <td>
                            <button class="action-btn" onclick="openEditModal(${user.userId}, '${user.username}', '${user.name}', '${user.email}', '${user.role}')">
                                <i class="fas fa-edit"></i> Edit
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Edit User Modal -->
    <div id="editUserModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="document.getElementById('editUserModal').style.display='none'">&times;</span>
            <h3>Edit User</h3>
            <form method="post" action="admin-dashboard">
                <input type="hidden" name="action" value="update-user">
                <input type="hidden" name="redirectTo" value="manage-users">
                <input type="hidden" id="editUserId" name="userId">
                
                <div class="form-group">
                    <label>Username</label>
                    <input type="text" id="editUsername" name="username" required>
                </div>
                <div class="form-group">
                    <label>Name</label>
                    <input type="text" id="editName" name="name" required>
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" id="editEmail" name="email" required>
                </div>
                <div class="form-group">
                    <label>Role</label>
                    <select id="editRole" name="role" required>
                        <option value="user">User</option>
                        <option value="librarian">Librarian</option>
                    </select>
                </div>
                <button type="submit" class="btn-primary">Update User</button>
            </form>
        </div>
    </div>

    <script>
        function openEditModal(id, username, name, email, role) {
            document.getElementById('editUserId').value = id;
            document.getElementById('editUsername').value = username;
            document.getElementById('editName').value = name;
            document.getElementById('editEmail').value = email;
            document.getElementById('editRole').value = role;
            document.getElementById('editUserModal').style.display = 'block';
        }
    </script>
</div>