<%@ page import="java.net.URLEncoder" %>
<%@ page import="vn.edu.hcmuaf.fit.webbanquanao.user.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác minh OTP</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/verify.css">
    <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/imgs/Favicon/favicon-32x32.png" type="image/png">
    <!-- Các Frameworks -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
          integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/header-footer.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/main.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/bootstrap-4.6.2/css/bootstrap.min.css">
</head>
<body>
<%
    // Kiểm tra nếu không có email để xác minh thì không được vào
    String emailVerify = (String) session.getAttribute("emailVerify");
    if (emailVerify == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String error = (String) session.getAttribute("error");
    if (error != null) {
        session.removeAttribute("error");
    }
%>
<div class="verify-container">
    <h2 class="text-center">Xác minh tài khoản</h2>
    <p class="text-center">Vui lòng nhập mã OTP đã được gửi tới email: <b><%= emailVerify %></b></p>

    <% if (error != null) { %>
    <div class="alert alert-danger text-center">
        <%= error %>
    </div>
    <% } %>

    <form action="verifyOTP" method="POST" class="text-center">
        <div class="input-box mb-3">
            <input type="text" name="otp" placeholder="Nhập mã OTP" required class="form-control" />
        </div>
        <button type="submit" class="btn btn-primary">Xác minh</button>
    </form>

    <p class="text-center mt-3">
        Không nhận được mã?
        <a href="resend-otp">Gửi lại mã</a>
    </p>
</div>
</body>
</html>
