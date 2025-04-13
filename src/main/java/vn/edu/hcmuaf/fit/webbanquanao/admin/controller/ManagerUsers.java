package vn.edu.hcmuaf.fit.webbanquanao.admin.controller;

import com.google.gson.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUser;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AUserService;

@WebServlet(name = "AdminUserController", value = "/admin/manager-users")
public class ManagerUsers extends HttpServlet {
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";
    private static final String STAFF_ROLE = "USER";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy tham số 'username' từ yêu cầu (nếu có)
        String username = request.getParameter("username");
        // Tạo đối tượng UserService để truy vấn dữ liệu người dùng
        AUserService userService = new AUserService();
        // Kiểm tra xem có tham số 'username' hay không
        if (username != null && !username.isEmpty()) {
            // Nếu có username, tìm người dùng theo username
            AUser user = userService.getUserByUsername(username);
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
            Map<String, AUser> users = userService.showUser();
            List<AUser> userList = users.values().stream().collect(Collectors.toList());

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

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        try {
//            // Đọc dữ liệu JSON từ client
//            StringBuilder jsonBuffer = new StringBuilder();
//            String line;
//            try (BufferedReader reader = request.getReader()) {
//                while ((line = reader.readLine()) != null) {
//                    jsonBuffer.append(line);
//                }
//            }
//            String json = jsonBuffer.toString();
//
//            // Parse JSON thành đối tượng User
//            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
//            AUser user = gson.fromJson(json, AUser.class);
//
//            // Kiểm tra các trường dữ liệu, đảm bảo không có giá trị null
//            if (user.getUserName() == null || user.getPassWord() == null) {
//                throw new IllegalArgumentException("Tài khoản và mật khẩu không được để trống");
//            }
//
//            // Gọi service để tạo user
//            AUserService userService = new AUserService();
//            boolean isCreated = userService.createUser(user);
//
//            // Phản hồi
//            JsonObject jsonResponse = new JsonObject();
//            if (isCreated) {
//                jsonResponse.addProperty("message", "Người dùng đã được tạo thành công!");
//                response.setStatus(HttpServletResponse.SC_CREATED);
//            } else {
//                jsonResponse.addProperty("message", "Không thể tạo người dùng. Tên đăng nhập đã tồn tại.");
//                response.setStatus(HttpServletResponse.SC_CONFLICT);
//            }
//            response.getWriter().write(gson.toJson(jsonResponse));
//        } catch (JsonSyntaxException e) {
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().write("{\"message\": \"Dữ liệu JSON không hợp lệ\"}");
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().write("{\"message\": \"" + e.getMessage() + "\"}");
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().write("{\"message\": \"Đã xảy ra lỗi trong quá trình xử lý yêu cầu: " + e.getMessage() + "\"}");
//        }
//    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            AUser user = gson.fromJson(json, AUser.class);
            // Log user nhận được
            System.out.println("User received: " + user);
            // Kiểm tra các trường dữ liệu, đảm bảo không có giá trị null
            if (user.getId() == null || user.getUserName() == null) {
                throw new IllegalArgumentException("Missing required fields");
            }
            // Gọi service để cập nhật
            AUserService userService = new AUserService();
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Đọc dữ liệu JSON từ body
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }
            String json = jsonBuffer.toString();
            System.out.println("Received JSON: " + json);

            // Parse JSON để lấy username
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            String username = jsonObject.get("userName").getAsString();

            // Kiểm tra username hợp lệ
            if (username == null || username.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Tên người dùng không được để trống\"}");
                return;
            }

            // Kiểm tra role Admin
            AUserService userService = new AUserService();
            List<String> userRoles = userService.getRoleNameByUserName(username);
            if (userRoles == null || userRoles.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"message\": \"Không thể xác định vai trò người dùng\"}");
                return;
            }

            if (userRoles.contains(ADMIN_ROLE)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write("{\"message\": \"Không thể xóa người dùng cùng vai trò\"}");
                return;
            }

            // Gọi service để thực hiện xóa mềm
            boolean isDeleted = userService.deleteUser(username);

            if (isDeleted) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"Xóa mềm người dùng thành công\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"message\": \"Không tìm thấy hoặc không thể xóa người dùng\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Có lỗi xảy ra: " + e.getMessage() + "\"}");
        }
    }

}