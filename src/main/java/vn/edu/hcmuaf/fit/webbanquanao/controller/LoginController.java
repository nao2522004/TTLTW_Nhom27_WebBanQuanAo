package vn.edu.hcmuaf.fit.webbanquanao.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.service.AuthService;

import java.io.IOException;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("auth") != null) {
            // Nếu người dùng đã đăng nhập, điều hướng đến user.jsp
            response.sendRedirect("./user.jsp");
        } else {
            // Nếu chưa đăng nhập, hiển thị trang đăng nhập
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
            session.setAttribute("auth", user); // Lưu người dùng vào session với tên 'auth'

            if (user.getRoleId() == 1) {
                response.sendRedirect("./admin.jsp");
            } else {
                response.sendRedirect("homePage");
            }
        } else {
            request.setAttribute("error", "Dang Nhap Khong Thanh Cong");
            request.getRequestDispatcher("./login.jsp").forward(request, response);
        }
    }
}