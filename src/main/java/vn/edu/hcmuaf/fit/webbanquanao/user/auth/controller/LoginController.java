package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.service.AuthService;

import java.io.IOException;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Nếu đã đăng nhập, chuyển hướng đến trang chính
        if (session.getAttribute("auth") != null) {
            response.sendRedirect(request.getContextPath() + "/homePage");
        } else {
            request.getRequestDispatcher("./login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("passWord");
        String redirect = request.getParameter("redirect");

        AuthService service = new AuthService();
        User user = service.checkLogin(userName, password);

        if (user != null) {
            HttpSession session = request.getSession();

            // Lưu thông tin user vào session
            session.setAttribute("auth", user);
            session.setAttribute("roles", user.getRoles());
            session.setAttribute("permissions", user.getPermissions());

            // Ưu tiên chuyển hướng đến trang redirect nếu có
            if (redirect != null && !redirect.isEmpty()) {
                response.sendRedirect(request.getContextPath() + redirect);
            } else if (user.hasRole("ADMIN")) {
                response.sendRedirect(request.getContextPath() + "/admin.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/homePage");
            }
        } else {
            request.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            request.getRequestDispatcher("./login.jsp").forward(request, response);
        }
    }
}
