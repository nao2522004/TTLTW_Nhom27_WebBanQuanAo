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
            session.setAttribute("otpError", "Phiên đã hết hạn. Vui lòng đăng ký lại.");
            response.sendRedirect("register.jsp");
            return;
        }

        String storedOTP = OTPStorage.getOTP(tempUser.getEmail());

        if (storedOTP == null || !storedOTP.equals(otpInput)) {
            session.setAttribute("otpError", "Mã OTP không chính xác!");
            response.sendRedirect("verify.jsp");
        } else {
            // OTP hợp lệ -> Lưu user vào database
            boolean isSuccess = userDao.registerUser(tempUser);
            if (isSuccess) {
                OTPStorage.removeOTP(tempUser.getEmail());
                session.setAttribute("auth", tempUser);
                response.sendRedirect("homePage");
            } else {
                session.setAttribute("otpError", "Lỗi đăng ký tài khoản, vui lòng thử lại.");
                response.sendRedirect("login.jsp");
            }
        }
    }
}

