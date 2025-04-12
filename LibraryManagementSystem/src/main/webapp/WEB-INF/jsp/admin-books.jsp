<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="admin-section">
    <div class="section-header">
        <h3><i class="fas fa-book"></i> Manage Books</h3>
        <button class="btn-primary" onclick="document.getElementById('addBookModal').style.display='block'">
            <i class="fas fa-plus"></i> Add New Book
        </button>
    </div>

    <div class="table-responsive">
        <table>
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>ISBN</th>
                    <th>Category</th>
                    <th>Total Copies</th>
                    <th>Available</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${books}" var="book">
                    <tr>
                        <td>${book.title}</td>
                        <td>${book.author}</td>
                        <td>${book.isbn}</td>
                        <td>${book.category}</td>
                        <td>${book.totalCopies}</td>
                        <td>${book.availableCopies}</td>
                        <td>
                            <button class="action-btn" onclick="openEditModal(${book.bookId}, '${book.title}', '${book.author}', '${book.isbn}', '${book.category}', ${book.totalCopies}, ${book.availableCopies})">
                                <i class="fas fa-edit"></i> Edit
                            </button>
                            <form method="post" action="admin-dashboard" style="display:inline;">
                                <input type="hidden" name="action" value="delete-book">
                                <input type="hidden" name="bookId" value="${book.bookId}">
                                <input type="hidden" name="redirectTo" value="manage-books">
                                <button type="submit" class="action-btn reject" onclick="return confirm('Are you sure you want to delete this book?')">
                                    <i class="fas fa-trash"></i> Delete
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Add Book Modal -->
    <div id="addBookModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="document.getElementById('addBookModal').style.display='none'">&times;</span>
            <h3>Add New Book</h3>
            <form method="post" action="admin-dashboard">
                <input type="hidden" name="action" value="add-book">
                <input type="hidden" name="redirectTo" value="manage-books">
                
                <div class="form-group">
                    <label>Title</label>
                    <input type="text" name="title" required>
                </div>
                <div class="form-group">
                    <label>Author</label>
                    <input type="text" name="author" required>
                </div>
                <div class="form-group">
                    <label>ISBN</label>
                    <input type="text" name="isbn" required>
                </div>
                <div class="form-group">
                    <label>Category</label>
                    <input type="text" name="category" required>
                </div>
                <div class="form-group">
                    <label>Total Copies</label>
                    <input type="number" name="totalCopies" min="1" required>
                </div>
                <div class="form-group">
                    <label>Available Copies</label>
                    <input type="number" name="availableCopies" min="0" required>
                </div>
                <button type="submit" class="btn-primary">Add Book</button>
            </form>
        </div>
    </div>

    <!-- Edit Book Modal -->
    <div id="editBookModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="document.getElementById('editBookModal').style.display='none'">&times;</span>
            <h3>Edit Book</h3>
            <form method="post" action="admin-dashboard">
                <input type="hidden" name="action" value="update-book">
                <input type="hidden" name="redirectTo" value="manage-books">
                <input type="hidden" id="editBookId" name="bookId">
                
                <div class="form-group">
                    <label>Title</label>
                    <input type="text" id="editTitle" name="title" required>
                </div>
                <div class="form-group">
                    <label>Author</label>
                    <input type="text" id="editAuthor" name="author" required>
                </div>
                <div class="form-group">
                    <label>ISBN</label>
                    <input type="text" id="editIsbn" name="isbn" required>
                </div>
                <div class="form-group">
                    <label>Category</label>
                    <input type="text" id="editCategory" name="category" required>
                </div>
                <div class="form-group">
                    <label>Total Copies</label>
                    <input type="number" id="editTotalCopies" name="totalCopies" min="1" required>
                </div>
                <div class="form-group">
                    <label>Available Copies</label>
                    <input type="number" id="editAvailableCopies" name="availableCopies" min="0" required>
                </div>
                <button type="submit" class="btn-primary">Update Book</button>
            </form>
        </div>
    </div>

    <script>
        function openEditModal(id, title, author, isbn, category, totalCopies, availableCopies) {
            document.getElementById('editBookId').value = id;
            document.getElementById('editTitle').value = title;
            document.getElementById('editAuthor').value = author;
            document.getElementById('editIsbn').value = isbn;
            document.getElementById('editCategory').value = category;
            document.getElementById('editTotalCopies').value = totalCopies;
            document.getElementById('editAvailableCopies').value = availableCopies;
            document.getElementById('editBookModal').style.display = 'block';
        }
    </script>
</div>