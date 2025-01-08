package vn.edu.hcmuaf.fit.webbanquanao.controller.admin;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.service.AuthService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import vn.edu.hcmuaf.fit.webbanquanao.service.UserService;

@WebServlet(name = "AdminUserController", value = "/admin/manager-users")
public class AdminUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Lấy tham số 'username' từ yêu cầu (nếu có)
        String username = request.getParameter("username");

        // Tạo đối tượng UserService để truy vấn dữ liệu người dùng
        UserService userService = new UserService();

        // Kiểm tra xem có tham số 'username' hay không
        if (username != null && !username.isEmpty()) {
            // Nếu có username, tìm người dùng theo username
            User user = userService.getUserByUsername(username);

            if (user != null) {
                // Tạo Gson với TypeAdapter cho LocalDateTime
                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Đăng ký adapter
                        .create();

                // Thiết lập kiểu dữ liệu trả về là JSON
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                PrintWriter out = response.getWriter();
                String json = gson.toJson(user); // Chuyển đổi đối tượng người dùng thành JSON
                out.print(json);
                out.flush();
            } else {
                // Nếu không tìm thấy người dùng, trả về lỗi 404
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"message\": \"Khong tim thay user\"}");
            }
        } else {
            // Nếu không có 'username', trả về tất cả người dùng
            Map<String, User> users = userService.showUser();
            List<User> userList = users.values().stream().collect(Collectors.toList());

            // Tạo Gson với TypeAdapter cho LocalDateTime
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Đăng ký adapter
                    .create();

            // Thiết lập kiểu dữ liệu trả về là JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            String json = gson.toJson(userList); // Sử dụng Gson đã đăng ký adapter
            out.print(json);
            out.flush();
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Đọc JSON từ body
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }
            String json = jsonBuffer.toString();

            // Log JSON nhận được
            System.out.println("JSON body received: " + json);

            // Parse JSON
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
            User user = gson.fromJson(json, User.class);

            // Log user nhận được
            System.out.println("User received: " + user);

            // Kiểm tra các trường dữ liệu, đảm bảo không có giá trị null
            if (user.getId() == null || user.getUserName() == null) {
                throw new IllegalArgumentException("Missing required fields");
            }

            // Gọi service để cập nhật
            UserService userService = new UserService();
            boolean isUpdated = userService.updateUser(user, user.getUserName());

            // Phản hồi
            JsonObject jsonResponse = new JsonObject();
            if (isUpdated) {
                jsonResponse.addProperty("message", "User updated successfully");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                jsonResponse.addProperty("message", "Failed to update user");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            response.getWriter().write(gson.toJson(jsonResponse));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Invalid JSON format\"}");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Missing required fields\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Error processing request: " + e.getMessage() + "\"}");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }


}
