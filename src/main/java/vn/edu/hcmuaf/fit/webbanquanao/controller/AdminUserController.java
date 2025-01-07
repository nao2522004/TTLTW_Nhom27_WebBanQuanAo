package vn.edu.hcmuaf.fit.webbanquanao.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.service.AuthService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "AdminUserController", value = "/adminUserController")
public class AdminUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Lấy danh sách người dùng từ UserDao
        AuthService authService = new AuthService();
        Map<String, User> users = authService.showuser();
        List<User> userList = users.values().stream().collect(Collectors.toList());

        request.setAttribute("userList", userList);
        request.getRequestDispatcher("admin.jsp").forward(request, response);

//        // Thiết lập kiểu dữ liệu trả về là JSON
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        PrintWriter out = response.getWriter();
//        // Chuyển danh sách người dùng thành JSON và gửi trả về frontend
//        out.print(new com.google.gson.Gson().toJson(userList));
//        out.flush();
//
//        System.out.println("User list: " + new com.google.gson.Gson().toJson(userList));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}
