<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 21/03/2025
  Time: 9:34 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác minh OTP</title>
    <link rel="stylesheet" href="assets/css/verify.css">
    <!-- Favicon -->
    <link rel="shortcut icon" href="assets/imgs/Favicon/favicon-32x32.png" type="image/png">
    <!-- Frameworks -->
    <!-- Reset CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
          integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="./assets/css/base.css">
    <link rel="stylesheet" href="./assets/css/header-footer.css">
    <link rel="stylesheet" href="./assets/css/main.css">

    <!-- Bootstrap 4.6.2 CSS -->
    <link rel="stylesheet" href="./assets/bootstrap-4.6.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>Xác minh tài khoản</h2>
    <p>Vui lòng nhập mã OTP đã được gửi tới email của bạn.</p>

    <% String error = (String) request.getSession().getAttribute("error"); %>
    <% if (error != null) { %>
    <p class="error-message" style="color: red;"><%= error %></p>
    <% request.getSession().removeAttribute("error"); %>
    <% } %>

    <form action="verifyOTP" method="POST">
    <div class="input-box">
            <input type="text" name="otp" placeholder="Nhập mã OTP" required>
        </div>
        <button type="submit">Xác minh</button>
    </form>

    <p>Không nhận được mã? <a href="resend-otp">Gửi lại mã</a></p>


</div>
</body>
</html>

