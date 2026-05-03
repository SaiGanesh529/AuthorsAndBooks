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
        <li><a href="${pageContext.request.contextPath}/authors" class="active">Authors</a></li>
        <li><a href="${pageContext.request.contextPath}/books">Books</a></li>
    </ul>
</nav>

<div class="container">

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">❌ ${errorMessage}</div>
    </c:if>

    <div class="page-header">
        <h2>✍️ ${formAction}</h2>
        <a href="${pageContext.request.contextPath}/authors" class="btn btn-secondary">← Back to Authors</a>
    </div>

    <div class="card form-card">
        <div class="card-header">${formAction}</div>
        <div class="card-body">

            <%-- Set action URL based on whether editing or adding --%>
            <c:set var="actionUrl" value="/authors/add" />
            <c:if test="${author.id != null}">
                <c:set var="actionUrl" value="/authors/edit/${author.id}" />
            </c:if>

            <%-- Use Spring form tag for model binding --%>
            <form:form action="${pageContext.request.contextPath}${actionUrl}"
                       method="post" modelAttribute="author">

                <div class="form-group">
                    <label for="name">Full Name *</label>
                    <form:input path="name" id="name" cssClass="form-control"
                                placeholder="e.g. George Orwell" cssErrorClass="form-control is-invalid"/>
                    <form:errors path="name" cssClass="error-text"/>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="nationality">Nationality *</label>
                        <form:input path="nationality" id="nationality" cssClass="form-control"
                                    placeholder="e.g. British" cssErrorClass="form-control is-invalid"/>
                        <form:errors path="nationality" cssClass="error-text"/>
                    </div>
                    <div class="form-group">
                        <label for="birthYear">Birth Year</label>
                        <form:input path="birthYear" id="birthYear" type="number"
                                    cssClass="form-control" placeholder="e.g. 1903"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="biography">Biography</label>
                    <form:textarea path="biography" id="biography" cssClass="form-control"
                                   rows="4" placeholder="Brief biography of the author..."/>
                </div>

                <div style="display:flex;gap:1rem;margin-top:1.5rem;">
                    <button type="submit" class="btn btn-primary">💾 ${formAction}</button>
                    <a href="${pageContext.request.contextPath}/authors" class="btn btn-secondary">Cancel</a>
                </div>

            </form:form>

        </div>
    </div>
</div>

<footer>&copy; 2024 Library System — Sai Shashank Chinthala</footer>
</body>
</html>
