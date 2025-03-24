//package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.*;
//import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUser;
//import vn.edu.hcmuaf.fit.webbanquanao.user.service.AuthService;
//
//import java.io.IOException;
//
//@WebServlet(name = "LoginController", value = "/login")
//public class LoginController extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        if (session.getAttribute("auth") != null) {
//            response.sendRedirect("./homePage");
//        } else {
//            request.getRequestDispatcher("./login.jsp").forward(request, response);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String userName = request.getParameter("userName");
//        String password = request.getParameter("passWord");
//
//        AuthService service = new AuthService();
//        AUser user = service.checkLogin(userName, password);
//
//        if (user != null) {
//            HttpSession session = request.getSession();
//            session.setAttribute("auth", user); // Lưu người dùng vào session
//
//            if (user.getRoles().contains("ADMIN")) {
//                response.sendRedirect("/admin");
//            } else if (user.getRoles().contains("USER")) {
//                response.sendRedirect("/user");
//            } else if (user.getRoles().contains("VIEWER")) {
//                response.sendRedirect("/viewer");
//            } else {
//                response.sendRedirect("/homePage");
//            }
//        } else {
//            request.setAttribute("error", "Đăng nhập không thành công");
//            request.getRequestDispatcher("./login.jsp").forward(request, response);
//        }
//    }
//}
