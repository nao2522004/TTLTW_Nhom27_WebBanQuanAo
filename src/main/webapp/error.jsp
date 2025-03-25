<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
    <div class="alert alert-danger"><%= errorMessage %></div>
    <% } %>
</head>
<body>

</body>
</html>
