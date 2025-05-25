package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.dao.TokenForgotDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.model.TokenForgotPassword;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/change-password")
public class ConfirmChangePasswordServlet extends HttpServlet {
    private TokenForgotDao tokenDao = new TokenForgotDao();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");

        TokenForgotPassword tokenData = tokenDao.getTokenForgot(token);

        if (tokenData != null && tokenData.getExpiresAt().isAfter(LocalDateTime.now())) {
            // Token hợp lệ và chưa hết hạn
            req.setAttribute("userId", tokenData.getUserId());
            req.getRequestDispatcher("/change-password.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Link xác nhận không hợp lệ hoặc đã hết hạn.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}

