package vn.edu.hcmuaf.fit.webbanquanao.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.Role;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.service.AuthService;

import java.io.IOException;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("auth") == null) {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
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

            response.sendRedirect("./index.jsp");
            if (user.getRoleId() == 1) {
                response.sendRedirect("./admin.jsp");
            } else {
                response.sendRedirect("./index.jsp");
            }
        } else {
            request.setAttribute("error", "Dang Nhap Khong Thanh Cong");
            request.getRequestDispatcher("./admin.jsp").forward(request, response);
        }


    }
}