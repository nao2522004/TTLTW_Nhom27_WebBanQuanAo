<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên Mật Khẩu</title>
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
    <link rel="stylesheet" href="./assets/css/main.css">
    <link rel="stylesheet" href="./assets/css/header-footer.css">
    <link rel="stylesheet" href="./assets/css/reset-password.css">

    <!-- Bootstrap 4.6.2 CSS -->
    <link rel="stylesheet" href="./assets/bootstrap-4.6.2/css/bootstrap.min.css">

</head>

<body>
    <div>
        <header id="header"><%@ include file="assets/includes/header.jsp"%></header>
    </div>

    <div class="container active no-padding">
        <div class="form-box">
            <form action="your-password-reset-endpoint" method="POST" onsubmit="return showConfirmationPopup(event)">
                <h1>Xác Nhận Lại Mật Khẩu</h1>
                <div class="input-box">
                    <input type="password" placeholder="Mật Khẩu Mới" required>
                    <button type="button" class="toggle-password" onclick="togglePassword(this)">
                        <i class="fa-solid fa-eye"></i>
                    </button>
                </div>
                <div class="input-box">
                    <input type="password" placeholder="Xác Nhận Lại Mật Khẩu" required>
                    <button type="button" class="toggle-password" onclick="togglePassword(this)">
                        <i class="fa-solid fa-eye"></i>
                    </button>
                </div>
                <button type="submit" class="btn btn-primary btn-lg">Xác Nhận</button>
                <div class="info-box">
                    <p>Bạn có thể liên hệ với chúng tôi qua email để nhận sự hỗ trợ <a
                            href="mailto: lasmanate@gmail.com"> lasmanate@gmail.com</a></p>
                </div>
            </form>
        </div>
    </div>

    <!-- Popup thông báo -->
    <div class="popup" id="confirmationPopup">
        <div class="popup-content">
            <p>Mật khẩu của bạn đã được cập nhật thành công!</p>
            <button type="button" class="btn btn-primary" onclick="redirectToHome()">Quay về Trang Chủ</button>
        </div>
    </div>

    <div>
        <footer id="footer"><%@ include file="assets/includes/footer.jsp"%></footer>
    </div>

    <script src="./assets/js/reset-password.js"></script>

    <!-- base js -->
    <script src="./assets/js/base.js"></script>
</body>

</html>