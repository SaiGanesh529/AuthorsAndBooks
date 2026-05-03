<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Library Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%-- Navigation --%>
<nav class="navbar">
    <a href="${pageContext.request.contextPath}/" class="navbar-brand">
        <span>📚</span> Library System
    </a>
    <ul class="navbar-links">
        <li><a href="${pageContext.request.contextPath}/" class="active">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/authors">Authors</a></li>
        <li><a href="${pageContext.request.contextPath}/books">Books</a></li>
    </ul>
</nav>

<div class="container">

    <%-- Hero --%>
    <div class="hero">
        <h1>📚 Book &amp; Author Management System</h1>
        <p>Manage your library collection with ease. Add, view and update books and their authors.</p>
    </div>

    <%-- Stats --%>
    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-number">${totalBooks}</div>
            <div class="stat-label">📖 Total Books</div>
        </div>
        <div class="stat-card">
            <div class="stat-number">${totalAuthors}</div>
            <div class="stat-label">✍️ Total Authors</div>
        </div>
    </div>

    <%-- Quick Actions --%>
    <div class="card">
        <div class="card-header">⚡ Quick Actions</div>
        <div class="card-body">
            <div style="display:flex; gap:1rem; flex-wrap:wrap;">
                <a href="${pageContext.request.contextPath}/authors/add" class="btn btn-primary">➕ Add Author</a>
                <a href="${pageContext.request.contextPath}/books/add"   class="btn btn-success">➕ Add Book</a>
                <a href="${pageContext.request.contextPath}/authors"     class="btn btn-secondary">👥 View Authors</a>
                <a href="${pageContext.request.contextPath}/books"       class="btn btn-secondary">📚 View Books</a>
            </div>
        </div>
    </div>

    <%-- Recent Books (using JOIN data) --%>
    <div class="card">
        <div class="card-header">📋 All Books in Library (INNER JOIN View)</div>
        <div class="card-body">
            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Title</th>
                            <th>Author</th>
                            <th>Genre</th>
                            <th>Year</th>
                            <th>Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${recentBooks}" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td><strong>${item.bookTitle}</strong></td>
                            <td>${item.authorName}</td>
                            <td><span class="badge badge-genre">${item.genre}</span></td>
                            <td>${item.publicationYear}</td>
                            <td>$${item.price}</td>
                        </tr>
                        </c:forEach>
                        <c:if test="${empty recentBooks}">
                            <tr><td colspan="6" style="text-align:center;color:#888;">No books found.</td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</div>

<footer>
    &copy; 2024 Book-Author Management System &mdash; Developed by Sai Shashank Chinthala
</footer>

</body>
</html>
