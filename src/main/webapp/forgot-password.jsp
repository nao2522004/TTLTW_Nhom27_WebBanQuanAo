<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="assets/includes/head.jsp"%>
    <title>Quên Mật Khẩu</title>
    <link rel="stylesheet" href="./assets/css/main.css">
    <link rel="stylesheet" href="./assets/css/forgot-password.css">
</head>

<body>
    <div>
        <header id="header"><%@ include file="assets/includes/header.jsp"%></header>
    </div>

    <div class="container active no-padding mx-auto">
        <div class="form-box">
            <form action="forgotPassword" method="POST">
                <h1>Bạn Quên Mật Khẩu?</h1>
                <p>Vui lòng nhập email hoặc nhập số điện thoại đã đăng ký của bạn để khôi phục mật khẩu</p>

                <div class="input-box">
                    <input type="email" name="email" placeholder="Email" required>
                    <i class="fa-solid fa-envelope"></i>
                </div>


                <!-- Hiển thị thông báo thành công hoặc lỗi -->
                <c:if test="${not empty message}">
                    <div class="alert alert-success">${message}</div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <button type="submit" class="btn btn-primary btn-lg" onclick="openOtpPopup()">Gửi Mã OTP</button>
                <p>Quay lại trang <a href="login.jsp" style="color: #335d4a !important;">Đăng Nhập</a></p>
                <p>Hoặc đăng nhập bằng các tài khoản mạng xã hội </p>
                <div class="social-icons">
                    <a href="#"><i class="fa-brands fa-facebook"></i></a>
                    <a href="#"><i class="fa-brands fa-google"></i></a>
                    <a href="#"><i class="fa-brands fa-x-twitter"></i></a>
                    <a href="#"><i class="fa-brands fa-instagram"></i></a>
                </div>
            </form>
        </div>

<%--        <!-- Popup nhập mã OTP -->--%>
<%--        <div class="otp-popup" id="otpPopup">--%>
<%--            <div class="otp-content">--%>
<%--                <h2>Nhập Mã OTP</h2>--%>
<%--                <p>Mã OTP đã được gửi đến số điện thoại của bạn. Vui lòng nhập mã để tiếp tục.</p>--%>
<%--                <div class="otp-input-box">--%>
<%--                    <input type="text" maxlength="1" class="otp-input" />--%>
<%--                    <input type="text" maxlength="1" class="otp-input" />--%>
<%--                    <input type="text" maxlength="1" class="otp-input" />--%>
<%--                    <input type="text" maxlength="1" class="otp-input" />--%>
<%--                    <input type="text" maxlength="1" class="otp-input" />--%>
<%--                    <input type="text" maxlength="1" class="otp-input" />--%>
<%--                </div>--%>
<%--                <button type="button" class="btn btn-primary btn-lg" onclick="validateOtp()">Xác Nhận</button>--%>
<%--                <button type="button" class="btn btn-secondary btn-lg" onclick="closeOtpPopup()">Hủy</button>--%>
<%--            </div>--%>
<%--        </div>--%>
    </div>

    <div>
        <footer id="footer"><%@ include file="assets/includes/footer.jsp"%></footer>
    </div>

    <script src="./assets/js/forgot-password.js"></script>

    <!-- base js -->
    <script src="./assets/js/base.js"></script>
</body>

</html>