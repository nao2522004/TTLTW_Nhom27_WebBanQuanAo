package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUser;
import vn.edu.hcmuaf.fit.webbanquanao.user.service.AuthService;

import java.io.IOException;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Kiểm tra nếu đã đăng nhập thì chuyển hướng về trang chính
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
        AUser user = service.checkLogin(userName, password);

        if (user != null) {
            HttpSession session = request.getSession();

            // Lưu thông tin người dùng, vai trò và quyền vào session
            session.setAttribute("auth", user);
            session.setAttribute("role", user.getRoleName());
            session.setAttribute("permissions", user.getPermissionName());

            // In ra console để kiểm tra
            System.out.println("User: " + user.getUserName());
            System.out.println("Role: " + user.getRoleName());
            System.out.println("Permissions: " + user.getPermissionName());

            // Điều hướng dựa trên vai trò của người dùng
            if ("ADMIN".equalsIgnoreCase(user.getRoleName())) {
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
