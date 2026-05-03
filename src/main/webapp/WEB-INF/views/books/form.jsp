<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"    uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${formAction} - Library System</title>
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

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">❌ ${errorMessage}</div>
    </c:if>

    <div class="page-header">
        <h2>📖 ${formAction}</h2>
        <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">← Back to Books</a>
    </div>

    <div class="card form-card">
        <div class="card-header">${formAction}</div>
        <div class="card-body">

            <%-- Set action URL based on whether editing or adding --%>
            <c:set var="actionUrl" value="/books/add" />
            <c:if test="${book.id != null}">
                <c:set var="actionUrl" value="/books/edit/${book.id}" />
            </c:if>

            <%-- Use Spring form tag for model binding --%>
            <form:form action="${pageContext.request.contextPath}${actionUrl}"
                       method="post" modelAttribute="book">

                <div class="form-group">
                    <label for="title">Book Title *</label>
                    <form:input path="title" id="title" cssClass="form-control"
                                placeholder="e.g. 1984" cssErrorClass="form-control is-invalid"/>
                    <form:errors path="title" cssClass="error-text"/>
                </div>

                <div class="form-group">
                    <label for="author.id">Author *</label>
                    <form:select path="author.id" id="author.id" cssClass="form-control">
                        <form:option value="" label="-- Select Author --"/>
                        <c:forEach var="author" items="${authors}">
                            <form:option value="${author.id}" label="${author.name}"/>
                        </c:forEach>
                    </form:select>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="isbn">ISBN</label>
                        <form:input path="isbn" id="isbn" cssClass="form-control"
                                    placeholder="e.g. 978-0451524935"/>
                    </div>
                    <div class="form-group">
                        <label for="genre">Genre</label>
                        <form:select path="genre" id="genre" cssClass="form-control">
                            <form:option value="" label="-- Select Genre --"/>
                            <form:option value="Fiction"           label="Fiction"/>
                            <form:option value="Non-Fiction"       label="Non-Fiction"/>
                            <form:option value="Dystopian"         label="Dystopian"/>
                            <form:option value="Fantasy"           label="Fantasy"/>
                            <form:option value="Psychological"     label="Psychological"/>
                            <form:option value="Magical Realism"   label="Magical Realism"/>
                            <form:option value="Historical Fiction" label="Historical Fiction"/>
                            <form:option value="Literary Fiction"  label="Literary Fiction"/>
                            <form:option value="Modernist"         label="Modernist"/>
                            <form:option value="Romance"           label="Romance"/>
                            <form:option value="Mystery"           label="Mystery"/>
                            <form:option value="Science Fiction"   label="Science Fiction"/>
                        </form:select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="publicationYear">Publication Year</label>
                        <form:input path="publicationYear" id="publicationYear" type="number"
                                    cssClass="form-control" placeholder="e.g. 1949"/>
                        <form:errors path="publicationYear" cssClass="error-text"/>
                    </div>
                    <div class="form-group">
                        <label for="price">Price ($)</label>
                        <form:input path="price" id="price" type="number" step="0.01"
                                    cssClass="form-control" placeholder="e.g. 12.99"/>
                        <form:errors path="price" cssClass="error-text"/>
                    </div>
                </div>

                <div style="display:flex;gap:1rem;margin-top:1.5rem;">
                    <button type="submit" class="btn btn-primary">💾 ${formAction}</button>
                    <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">Cancel</a>
                </div>

            </form:form>

        </div>
    </div>
</div>

<footer>&copy; 2024 Library System — Sai Shashank Chinthala</footer>
</body>
</html>
