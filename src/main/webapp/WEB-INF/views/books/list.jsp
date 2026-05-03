<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Books - Library System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/" class="navbar-brand"><span>📚</span> Library System</a>
    <ul class="navbar-links">
        <li><a href="${pageContext.request.contextPath}/">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/authors">Authors</a></li>
        <li><a href="${pageContext.request.contextPath}/books" class="active">Books</a></li>
    </ul>
</nav>

<div class="container">

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">✅ ${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">❌ ${errorMessage}</div>
    </c:if>

    <div class="page-header">
        <h2>📖 Books <span style="font-size:1rem;color:#666;">(${totalBooks} total)</span></h2>
        <a href="${pageContext.request.contextPath}/books/add" class="btn btn-success">➕ Add New Book</a>
    </div>

    <%-- Info banner about JOIN query --%>
    <div class="alert alert-info">
        ℹ️ This view is powered by a custom JPQL <strong>INNER JOIN</strong> query between the
        <code>books</code> and <code>authors</code> tables.
    </div>

    <div class="card">
        <div class="card-header">Book Catalogue (Books ⋈ Authors)</div>
        <div class="card-body" style="padding:0;">
            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Title</th>
                            <th>Author</th>
                            <th>Nationality</th>
                            <th>ISBN</th>
                            <th>Genre</th>
                            <th>Year</th>
                            <th>Price</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${booksWithAuthors}" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td><strong>${item.bookTitle}</strong></td>
                            <td>${item.authorName}</td>
                            <td><span class="badge badge-nation">${item.nationality}</span></td>
                            <td style="font-size:0.82rem;color:#555;">${item.isbn}</td>
                            <td><span class="badge badge-genre">${item.genre}</span></td>
                            <td>${item.publicationYear}</td>
                            <td>$${item.price}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/books/edit/${item.bookId}"
                                   class="btn btn-warning btn-sm">✏️ Edit</a>
                            </td>
                        </tr>
                        </c:forEach>
                        <c:if test="${empty booksWithAuthors}">
                            <tr><td colspan="9" style="text-align:center;color:#888;padding:2rem;">
                                No books found. <a href="${pageContext.request.contextPath}/books/add">Add one now!</a>
                            </td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</div>

<footer>&copy; 2024 Library System — Sai Shashank Chinthala</footer>
</body>
</html>
