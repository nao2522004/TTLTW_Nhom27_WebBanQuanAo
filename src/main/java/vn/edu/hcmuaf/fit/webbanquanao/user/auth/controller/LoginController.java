package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.user.service.AuthService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Kiểm tra nếu người dùng đã đăng nhập
        if (session.getAttribute("auth") != null) {
            response.sendRedirect(request.getContextPath() + "/homePage"); // Chuyển hướng đến homePage nếu đã đăng nhập
        } else {
            request.getRequestDispatcher("./login.jsp").forward(request, response); // Hiển thị trang đăng nhập nếu chưa đăng nhập
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

            // Lưu thông tin người dùng vào session
            session.setAttribute("auth", user);

            // Chuyển đổi role và permission từ String sang List
            session.setAttribute("roles", user.getRoleName());
            session.setAttribute("permissions", user.getPermissionName());

            System.out.println("roles: " + user.getRoleName());
            System.out.println("permissions: " + user.getPermissionName());

            // Kiểm tra nếu user có role ADMIN, chuyển hướng đến trang admin
            if (user.getRoleName().contains("ADMIN")) {
                response.sendRedirect(request.getContextPath() + "/admin.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/homePage");
            }
        } else {
            // Nếu đăng nhập thất bại, quay lại trang đăng nhập với thông báo lỗi
            request.setAttribute("error", "Đăng nhập không thành công. Vui lòng kiểm tra lại tên đăng nhập và mật khẩu.");
            request.getRequestDispatcher("./login.jsp").forward(request, response);
        }
    }
}
