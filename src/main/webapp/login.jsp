<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String error = (String) request.getAttribute("error");
    String username = (String) request.getAttribute("username");
    if (error == null) error = "";
    if (username == null) username = "";

    String signupError = (String) session.getAttribute("error");
    String email = request.getParameter("email");
    if (signupError == null) signupError = "";
    if (email == null) email = "";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Đăng Nhập</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Favicon -->
    <link rel="shortcut icon" href="assets/imgs/Favicon/favicon-32x32.png" type="image/png">
    <!-- Frameworks -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="./assets/css/base.css">
    <link rel="stylesheet" href="./assets/css/header-footer.css">
    <link rel="stylesheet" href="./assets/css/main.css">
    <link rel="stylesheet" href="./assets/css/login.css">
    <link rel="stylesheet" href="./assets/bootstrap-4.6.2/css/bootstrap.min.css">
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>

<body>
<header id="header"><%@ include file="assets/includes/header.jsp"%></header>

<div class="container no-padding">
    <!-- Login Form -->
    <div class="form-box login">
        <form action="login" method="post">
            <h1>Đăng Nhập</h1>
            <div class="input-box">
                <input type="text" placeholder="UserName" value="<%= username %>" name="userName" required>
                <i class="fa-solid fa-envelope"></i>
            </div>
            <div class="input-box">
                <input type="password" placeholder="Mật Khẩu" name="passWord" required>
                <button type="button" class="toggle-password" onclick="togglePassword(this)">
                    <i class="fa-solid fa-eye"></i>
                </button>
            </div>
            <div class="forgot-link">
                <a href="forgot-password.jsp">Quên mật khẩu?</a>
            </div>
            <input type="hidden" name="redirect" value="<%= request.getParameter("redirect") %>" />
            <button type="submit" class="btn btn-primary btn-lg">Đăng Nhập</button>

            <!-- Google reCAPTCHA -->
            <div class="recaptcha-wrapper">
                <div class="g-recaptcha" data-sitekey="6LcIsA4rAAAAAKTypNS-94XJI8a-Hq34isJYFdDQ"></div>
            </div>

            <p>hoặc đăng nhập bằng tài khoản mạng xã hội</p>
            <div class="social-icons">
                <a href="#"><i class="fa-brands fa-facebook"></i></a>
                <a href="${pageContext.request.contextPath}/google-login"><i class="fa-brands fa-google"></i></a>
                <a href="#"><i class="fa-brands fa-x-twitter"></i></a>
                <a href="#"><i class="fa-brands fa-instagram"></i></a>
            </div>
        </form>
    </div>
    <!-- END Login Form -->

    <!-- Register Form -->
    <div class="form-box register" id="signup-form">
        <form action="register" method="POST">
            <h1>Đăng Ký</h1>

            <div class="input-box">
                <input type="text" name="username" placeholder="Tên tài khoản" required>
                <i class="fa-solid fa-user"></i>
            </div>

            <div class="name-container">
                <div class="input-box">
                    <input type="text" name="firstName" placeholder="Họ" required>
                    <i class="fa-solid fa-user"></i>
                </div>
                <div class="input-box">
                    <input type="text" name="lastName" placeholder="Tên" required>
                    <i class="fa-solid fa-user"></i>
                </div>
            </div>

            <div class="input-box">
                <input type="email" name="email" placeholder="Email" value="<%= email %>" required>
                <i class="fa-solid fa-envelope"></i>
            </div>

            <div class="input-box">
                <input type="password" id="password" name="password" placeholder="Mật Khẩu" required>
                <button type="button" class="toggle-password" onclick="togglePassword(this)">
                    <i class="fa-solid fa-eye"></i>
                </button>
            </div>

            <!-- Google reCAPTCHA -->
            <div class="recaptcha-wrapper">
                <div class="g-recaptcha" data-sitekey="6LcIsA4rAAAAAKTypNS-94XJI8a-Hq34isJYFdDQ"></div>
            </div>

            <button type="submit" class="btn btn-primary btn-lg">Đăng Ký</button>

            <p>hoặc đăng ký bằng tài khoản mạng xã hội</p>
            <div class="social-icons">
                <a href="#"><i class="fa-brands fa-facebook"></i></a>
                <a href="google-login"><i class="fa-brands fa-google"></i></a>
                <a href="#"><i class="fa-brands fa-x-twitter"></i></a>
                <a href="#"><i class="fa-brands fa-instagram"></i></a>
            </div>
        </form>
    </div>
    <!-- END Register Form -->

    <!-- Toggle Box -->
    <div class="toggle-box">
        <div class="toggle-panel toggle-left">
            <h1>LASMANATE</h1>
            <p>Chào Mừng Bạn Trở Lại</p>
            <p>Bạn vẫn chưa có tài khoản?</p>
            <button class="btn register-btn btn-primary btn-lg">Đăng ký</button>
        </div>

        <div class="toggle-panel toggle-right">
            <h1>LASMANATE</h1>
            <p>Chào Mừng Bạn Đến Với Lasmanate</p>
            <p>Bạn đã có tài khoản?</p>
            <button class="btn login-btn btn-primary btn-lg">Đăng nhập</button>
        </div>
    </div>
    <!-- END Toggle Box -->
</div>

<footer id="footer"><%@ include file="assets/includes/footer.jsp"%></footer>

<script src="./assets/js/login.js"></script>
<script src="./assets/js/base.js"></script>

<script>
    // Hiển thị alert sau khi trang load xong
    window.onload = function() {
        <% if (!error.isEmpty()) { %>
        alert("<%= error %>");
        <% } %>
        <% if (!signupError.isEmpty()) { %>
        alert("<%= signupError %>");
        <% session.removeAttribute("error"); %>
        <% } %>
    };
</script>
</body>
</html>
