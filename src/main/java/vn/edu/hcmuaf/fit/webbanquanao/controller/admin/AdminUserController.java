package vn.edu.hcmuaf.fit.webbanquanao.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.service.AuthService;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet(name = "AdminUserController", value = "/admin/manager-users")
public class AdminUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Lấy danh sách người dùng từ UserDao
        AuthService authService = new AuthService();
        Map<String, User> users = authService.showuser();

        List<User> userList = users.values().stream().collect(Collectors.toList());

        // Tạo Gson với TypeAdapter cho LocalDateTime
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Đăng ký adapter
                .create();

        // Thiết lập kiểu dữ liệu trả về là JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String json = gson.toJson(userList); // Sử dụng Gson đã đăng ký adapter
        out.print(json);
        out.flush();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}
