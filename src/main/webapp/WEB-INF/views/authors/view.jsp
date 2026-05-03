<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${author.name} - Library System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/" class="navbar-brand"><span>📚</span> Library System</a>
    <ul class="navbar-links">
        <li><a href="${pageContext.request.contextPath}/">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/authors" class="active">Authors</a></li>
        <li><a href="${pageContext.request.contextPath}/books">Books</a></li>
    </ul>
</nav>

<div class="container">

    <div class="page-header">
        <h2>✍️ ${author.name}</h2>
        <div style="display:flex;gap:0.5rem;">
            <a href="${pageContext.request.contextPath}/authors/edit/${author.id}" class="btn btn-warning">✏️ Edit</a>
            <a href="${pageContext.request.contextPath}/authors"                   class="btn btn-secondary">← Back</a>
        </div>
    </div>

    <div class="card">
        <div class="card-header">Author Details</div>
        <div class="card-body">
            <table style="width:auto;border-collapse:collapse;">
                <tr><td style="padding:0.6rem 1rem;font-weight:600;color:#555;min-width:140px;">Name</td>
                    <td style="padding:0.6rem 1rem;">${author.name}</td></tr>
                <tr style="background:#f5f5f5;"><td style="padding:0.6rem 1rem;font-weight:600;color:#555;">Nationality</td>
                    <td style="padding:0.6rem 1rem;"><span class="badge badge-nation">${author.nationality}</span></td></tr>
                <tr><td style="padding:0.6rem 1rem;font-weight:600;color:#555;">Birth Year</td>
                    <td style="padding:0.6rem 1rem;">${author.birthYear}</td></tr>
                <tr style="background:#f5f5f5;"><td style="padding:0.6rem 1rem;font-weight:600;color:#555;">Biography</td>
                    <td style="padding:0.6rem 1rem;">${author.biography}</td></tr>
            </table>
        </div>
    </div>

    <div class="card">
        <div class="card-header">📚 Books by ${author.name}</div>
        <div class="card-body" style="padding:0;">
            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr><th>Title</th><th>ISBN</th><th>Genre</th><th>Year</th><th>Price</th></tr>
                    </thead>
                    <tbody>
                        <c:forEach var="book" items="${author.books}">
                        <tr>
                            <td><strong>${book.title}</strong></td>
                            <td>${book.isbn}</td>
                            <td><span class="badge badge-genre">${book.genre}</span></td>
                            <td>${book.publicationYear}</td>
                            <td>$${book.price}</td>
                        </tr>
                        </c:forEach>
                        <c:if test="${empty author.books}">
                            <tr><td colspan="5" style="text-align:center;color:#888;padding:1.5rem;">No books found for this author.</td></tr>
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
