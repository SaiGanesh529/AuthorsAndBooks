<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Authors - Library System</title>
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

    <%-- Flash Messages --%>
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">✅ ${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">❌ ${errorMessage}</div>
    </c:if>

    <div class="page-header">
        <h2>✍️ Authors <span style="font-size:1rem;color:#666;">(${totalAuthors} total)</span></h2>
        <a href="${pageContext.request.contextPath}/authors/add" class="btn btn-primary">➕ Add New Author</a>
    </div>

    <div class="card">
        <div class="card-header">Author Directory</div>
        <div class="card-body" style="padding:0;">
            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Name</th>
                            <th>Nationality</th>
                            <th>Birth Year</th>
                            <th>Biography</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="author" items="${authors}" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td><strong>${author.name}</strong></td>
                            <td><span class="badge badge-nation">${author.nationality}</span></td>
                            <td>${author.birthYear}</td>
                            <td style="max-width:250px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;" title="${author.biography}">
                                ${author.biography}
                            </td>
                            <td style="white-space:nowrap;">
                                <a href="${pageContext.request.contextPath}/authors/view/${author.id}" class="btn btn-secondary btn-sm">👁 View</a>
                                <a href="${pageContext.request.contextPath}/authors/edit/${author.id}" class="btn btn-warning btn-sm">✏️ Edit</a>
                            </td>
                        </tr>
                        </c:forEach>
                        <c:if test="${empty authors}">
                            <tr><td colspan="6" style="text-align:center;color:#888;padding:2rem;">
                                No authors found. <a href="${pageContext.request.contextPath}/authors/add">Add one now!</a>
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
