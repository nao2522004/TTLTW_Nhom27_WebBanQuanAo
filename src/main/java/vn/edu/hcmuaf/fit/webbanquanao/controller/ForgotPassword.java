package vn.edu.hcmuaf.fit.webbanquanao.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.dao.TokenForgotDao;
import vn.edu.hcmuaf.fit.webbanquanao.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.model.TokenForgotPassword;
import vn.edu.hcmuaf.fit.webbanquanao.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.service.ResetService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "forgotPassword", value = "/forgotPassword")
public class ForgotPassword extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        String email = request.getParameter("email").trim();

        try {
            User user = userDao.getUserByEmail(email);
            if (user == null || user.getId() == 0) {
                request.setAttribute("message", "email not found or user id is invalid");
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
                return;
            }


            ResetService resetService = new ResetService();
            String token = resetService.genarateToken();
            String linkReset = "http://localhost:8080/WebBanQuanAo/reset-password?token="+resetService.genarateToken();
            TokenForgotPassword newTokenForgot = new TokenForgotPassword(user.getId(), false, token, resetService.expiresAt());

            TokenForgotDao tokenForgotDao = new TokenForgotDao();
            boolean isInsert = tokenForgotDao.insertTokenForgot(newTokenForgot);
            if (!isInsert) {
                request.setAttribute("message", "have error in server");
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
                return;
            }
            boolean isSend = resetService.sendEmail(email, linkReset, user.getUserName());
            if (!isSend) {
                request.setAttribute("message", "can not send request");
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
                return;
            }
            request.setAttribute("message", "email request successfully");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace(); // Log lỗi để biết nguyên nhân
            request.setAttribute("message", "Database error: " + e.getMessage());
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            return;
        }

    }
}
