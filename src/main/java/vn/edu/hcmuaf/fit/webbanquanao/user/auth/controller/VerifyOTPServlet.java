package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.model.OTPStorage;
import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.io.IOException;

@WebServlet("/verifyOTP")
public class VerifyOTPServlet extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String otpInput = request.getParameter("otp");
        HttpSession session = request.getSession();
        User tempUser = (User) session.getAttribute("tempUser");

        if (tempUser == null) {
            // Nếu không có tempUser, session đã hết hạn, chuyển hướng đến trang đăng ký lại
            session.setAttribute("otpError", "Phiên đã hết hạn. Vui lòng đăng ký lại.");
            response.sendRedirect("register.jsp");
            return;
        }

        // Lấy OTP đã lưu từ bộ nhớ
        String storedOTP = OTPStorage.getOTP(tempUser.getEmail());

        // Kiểm tra xem OTP nhập vào có khớp không
        if (storedOTP == null || !storedOTP.equals(otpInput)) {
            session.setAttribute("otpError", "Mã OTP không chính xác!");
            response.sendRedirect("verify.jsp"); // Nếu không đúng OTP, chuyển lại trang verify
        } else {
            // Nếu OTP hợp lệ, đăng ký người dùng
            boolean isSuccess = userDao.registerUser(tempUser);
            if (isSuccess) {
                OTPStorage.removeOTP(tempUser.getEmail()); // Xóa OTP sau khi đăng ký thành công
                session.setAttribute("auth", tempUser); // Lưu người dùng vào session
                response.sendRedirect("homePage"); // Chuyển đến trang chủ sau khi đăng ký thành công
            } else {
                session.setAttribute("otpError", "Lỗi đăng ký tài khoản, vui lòng thử lại.");
                response.sendRedirect("login.jsp"); // Nếu lỗi đăng ký, chuyển về trang login
            }
        }

        // In thông tin OTP để kiểm tra (debug)
        System.out.println("Stored OTP: " + storedOTP);
        System.out.println("User input OTP: " + otpInput);
    }
}


