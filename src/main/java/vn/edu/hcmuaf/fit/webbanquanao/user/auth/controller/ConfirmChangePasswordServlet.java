package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;

import java.io.IOException;

@WebServlet("/change-password")
public class ConfirmChangePasswordServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");

        Integer userId = userDao.getUserIdByToken(token);

        if (userId != null) {
            // Token hợp lệ, hiển thị trang đổi mật khẩu
            req.setAttribute("userId", userId);
            req.getRequestDispatcher("/change-password.jsp").forward(req, resp);
        } else {
            resp.getWriter().write("Link xác nhận không hợp lệ hoặc đã hết hạn.");
        }
    }
}
