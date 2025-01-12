<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>Đăng Nhập</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
    <link rel="stylesheet" href="./assets/css/login.css">

    <!-- Bootstrap 4.6.2 CSS -->
    <link rel="stylesheet" href="./assets/bootstrap-4.6.2/css/bootstrap.min.css">

</head>

<body>
    <div>
        <header id="header"><%@ include file="assets/includes/header.jsp"%></header>
    </div>


    <div class="container no-padding">
        <%
            String error = (String) request.getAttribute("error");
            String username = (String) request.getAttribute("username");
            if(error==null) error="";
            if(username==null) username="";
        %>
        <!-- Login -->
        <div class="form-box login">
            <form action="login" method="post">
                <h1>Đăng Nhập</h1>
                <div class="input-box">
                    <input type="text" placeholder="UserName" value="<%=username%>" name="userName" required>
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
                <button type="submit" class="btn btn-primary btn-lg ">Đăng Nhập</button>
                <p style="color: red;"><%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %></p>
                <p>hoặc đăng nhập bằng tài khoản mạng xã hội</p>
                <div class="social-icons">
                    <a href="#"><i class="fa-brands fa-facebook"></i></a>
                    <a href="#"><i class="fa-brands fa-google"></i></a>
                    <a href="#"><i class="fa-brands fa-x-twitter"></i></a>
                    <a href="#"><i class="fa-brands fa-instagram"></i></a>
                </div>
            </form>
        </div>
        <!-- END: Login -->


        <!-- Register -->
        <div class="form-box register">
            <form action="">
                <h1>Đăng Ký</h1>
                <div class="input-box">
                    <input type="text" name="username" placeholder="Họ và Tên" required>
                    <i class="fa-solid fa-user"></i>
                </div>
                <div class="input-box">
                    <input type="text" placeholder="Email/Số Điện Thoại" required>
                    <i class="fa-solid fa-envelope"></i>
                </div>
                <div class="input-box">
                    <input type="password" placeholder="Mật Khẩu" required>
                    <button type="button" class="toggle-password" onclick="togglePassword(this)">
                        <i class="fa-solid fa-eye"></i>
                    </button>
                </div>
                <button type="submit" class="btn btn-primary btn-lg">Đăng Ký</button>
                <p>hoặc đăng ký bằng tài khoản mạng xã hội</p>
                <div class="social-icons">
                    <a href="#"><i class="fa-brands fa-facebook"></i></a>
                    <a href="#"><i class="fa-brands fa-google"></i></a>
                    <a href="#"><i class="fa-brands fa-x-twitter"></i></a>
                    <a href="#"><i class="fa-brands fa-instagram"></i></a>
                </div>
            </form>
        </div>
        <!-- END: Register -->

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
    </div>


    <div>
        <footer id="footer"><%@ include file="assets/includes/footer.jsp"%></footer>
    </div>

    <script src="./assets/js/login.js"></script>

    <!-- base js -->
    <script src="./assets/js/base.js"></script>
</body>

</html>