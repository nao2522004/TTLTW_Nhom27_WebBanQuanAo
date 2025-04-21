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

        AuthService service = new AuthService();
        User user = service.checkLogin(userName, password);

        if (user != null) {
            HttpSession session = request.getSession();

            // Lưu thông tin user vào session
            session.setAttribute("auth", user);
            session.setAttribute("roles", user.getRoles()); // Lưu danh sách roles
            session.setAttribute("permissions", user.getPermissions()); // Lưu quyền dạng Map

            session.setAttribute("hasLoggedUserInfo", true);

            System.out.println();
            System.out.println();
            System.out.println("Notify in LoginController:");
            System.out.println("Login success: " + userName);
            System.out.println("Roles: " + user.getRoles());
            System.out.println("Permissions: " + user.getPermissions());
            System.out.println();
            System.out.println();

            // Kiểm tra quyền admin
            if (user.hasRole("ADMIN")) {
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