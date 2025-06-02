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
            <form action="ResetPassword" method="POST">
                <h1>Xác Nhận Lại Mật Khẩu</h1>
                <div class="input-box">
                    <input type="email" name="email" value="${email}" readonly>
                    <i class="fa-solid fa-envelope"></i>
                </div>

                <div class="input-box">
                    <input type="password" name="newPassword" id="newPassword" placeholder="Mật Khẩu Mới" required>
                    <button type="button" class="toggle-password" onclick="togglePassword(this)">
                        <i class="fa-solid fa-eye"></i>
                    </button>
                </div>
                <p id="passwordStrength"></p>
                <div class="input-box">
                    <input type="password" name="confirmPassword" id="confirmPassword" placeholder="Xác Nhận Lại Mật Khẩu" required>
                    <button type="button" class="toggle-password"  onclick="togglePassword(this)">
                        <i class="fa-solid fa-eye"></i>
                    </button>
                </div>
                <p id="confirmPasswordMsg"></p>

                <button type="submit" class="btn btn-primary btn-lg">Xác Nhận</button>
                <div class="info-box">
                    <p>Bạn có thể liên hệ với chúng tôi qua email để nhận sự hỗ trợ <a
                            href="mailto: lasmanate@gmail.com"> lasmanate@gmail.com</a></p>
                </div>
            </form>
        </div>
    </div>


    <div>
        <footer id="footer"><%@ include file="assets/includes/footer.jsp"%></footer>
    </div>

    <script src="./assets/js/reset-password.js"></script>

    <!-- base js -->
    <script src="./assets/js/base.js"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#newPassword").on("input", function () {
                var password = $(this).val();
                var strengthMsg = "";
                var strengthColor = "red";

                if (password.length < 8) {
                    strengthMsg = "❌ Mật khẩu phải có ít nhất 8 ký tự!";
                } else if (!/[A-Z]/.test(password)) {
                    strengthMsg = "❌ Mật khẩu phải chứa ít nhất một chữ cái viết hoa!";
                } else if (!/[0-9]/.test(password)) {
                    strengthMsg = "❌ Mật khẩu phải chứa ít nhất một chữ số!";
                } else if (!/[^A-Za-z0-9]/.test(password)) {
                    strengthMsg = "⚠️ Mật khẩu nên chứa ít nhất một ký tự đặc biệt!";
                    strengthColor = "orange";
                } else {
                    strengthMsg = "✅ Mật khẩu mạnh!";
                    strengthColor = "green";
                }

                $("#passwordStrength").text(strengthMsg).css("color", strengthColor);
            });

            $("#confirmPassword").on("input", function () {
                validateConfirmPassword();
            });
        });

        function validateConfirmPassword() {
            var newPassword = $("#newPassword").val();
            var confirmPassword = $("#confirmPassword").val();
            var msg = "";
            var color = "red";

            if (confirmPassword.length > 0) {
                if (newPassword !== confirmPassword) {
                    msg = "❌ Mật khẩu xác nhận không khớp!";
                } else {
                    msg = "✅ Mật khẩu khớp!";
                    color = "green";
                }
            }

            $("#confirmPasswordMsg").text(msg).css("color", color);
        }

        function validateForm() {
            if ($("#newPassword").val() !== $("#confirmPassword").val()) {
                alert("Mật khẩu xác nhận không khớp! Vui lòng kiểm tra lại.");
                return false;
            }
            return true;
        }

    </script>




</body>

</html>