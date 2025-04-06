<%@ page import="vn.edu.hcmuaf.fit.webbanquanao.user.model.User" %>
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
    <h2 class="text-center">Xác minh tài khoản</h2>
    <p class="text-center">Vui lòng nhập mã OTP đã được gửi tới email của bạn.</p>
    <%
        User user = (User) session.getAttribute("auth");
        if (user == null) {
            // Nếu chưa đăng nhập, chuyển hướng đến trang login với tham số redirect
            String currentUrl = request.getRequestURI();
            String redirectUrl = currentUrl.substring(request.getContextPath().length());
            response.sendRedirect("login.jsp?redirect=" + URLEncoder.encode(redirectUrl, "UTF-8"));
            return;
        }
    %>

    <% String error = (String) request.getSession().getAttribute("error"); %>
    <% if (error != null) { %>
    <div class="alert alert-danger text-center">
        <%= error %>
    </div>
    <% request.getSession().removeAttribute("error"); %>
    <% } %>

    <!-- Form xác minh OTP -->
    <form action="verifyOTP" method="POST" class="text-center">
        <div class="input-box mb-3">
            <input type="text" name="otp" placeholder="Nhập mã OTP" required class="form-control" />
        </div>
        <button type="submit" class="btn btn-primary">Xác minh</button>
    </form>

    <p class="text-center mt-3">Không nhận được mã? <a href="resend-otp">Gửi lại mã</a></p>
</div>

<!-- Optional: Add a footer if needed -->
<footer class="footer text-center mt-5">
    <p>© 2025 Lasmanate. Tất cả quyền được bảo vệ.</p>
</footer>

</body>
</html>
