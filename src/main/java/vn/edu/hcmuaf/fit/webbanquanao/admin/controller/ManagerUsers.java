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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUser;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AUserService;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

@WebServlet(name = "AdminUserController", value = "/admin/manager-users")
public class ManagerUsers extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ManagerUsers.class);
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";
    private static final String STAFF_ROLE = "USER";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin người dùng từ session (tên lưu là "auth")
        HttpSession session = request.getSession(false);
        User authUser = (User) session.getAttribute("auth");
        String actorUsername = (authUser != null) ? authUser.getUserName() : "unknown";
        List<String> roles = (authUser != null) ? authUser.getRoles() : List.of();
        String ip = request.getRemoteAddr();

        // Lấy tham số 'username' từ yêu cầu (nếu có)
        String username = request.getParameter("username");

        AUserService userService = new AUserService();

        if (username != null && !username.isEmpty()) {
            AUser user = userService.getUserByUsername(username);
            if (user != null) {
                // Ghi log vào CSDL và console: xem chi tiết user
                UserLogsService.getInstance().logAction(
                        "INFO",
                        actorUsername,
                        roles,
                        "Xem chi tiết user: " + username,
                        ip
                );
                logger.info("User: {}, Action: View user details: {}", actorUsername, username);

                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                        .create();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.print(gson.toJson(user));
                out.flush();
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"message\": \"Không tìm thấy user\"}");

                UserLogsService.getInstance().logAction(
                        "WARN",
                        actorUsername,
                        roles,
                        "Cố gắng truy cập user không tồn tại: " + username,
                        ip
                );
                logger.warn("User: {}, User not found: {}", actorUsername, username);
            }
        } else {
            Map<String, AUser> users = userService.showUser();
            List<AUser> userList = users.values().stream().collect(Collectors.toList());

            UserLogsService.getInstance().logAction(
                    "INFO",
                    actorUsername,
                    roles,
                    "Xem danh sách tất cả user",
                    ip
            );
            logger.info("User: {}, Action: View all users", actorUsername);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(userList));
            out.flush();
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = LoggerFactory.getLogger(ManagerUsers.class);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Lấy thông tin người thao tác
        HttpSession session = request.getSession(false);
        User authUser = (session != null) ? (User) session.getAttribute("auth") : null;
        String actorUsername = (authUser != null) ? authUser.getUserName() : "unknown";
        List<String> roles = (authUser != null) ? authUser.getRoles() : List.of();
        String ip = request.getRemoteAddr();

        try {
            // Đọc JSON
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }
            String json = jsonBuffer.toString();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();
            AUser user = gson.fromJson(json, AUser.class);

            if (user.getUserName() == null || user.getPassWord() == null) {
                throw new IllegalArgumentException("Tài khoản và mật khẩu không được để trống");
            }

            AUserService userService = new AUserService();
            boolean isCreated = userService.addUser(user);

            JsonObject jsonResponse = new JsonObject();
            if (isCreated) {
                jsonResponse.addProperty("message", "Người dùng đã được tạo thành công!");
                response.setStatus(HttpServletResponse.SC_CREATED);
                logger.info("User: {}, Action: Created new user: {}", actorUsername, user.getUserName());
                UserLogsService.getInstance().logAction(
                        "INFO", actorUsername, roles, "Tạo user mới: " + user.getUserName(), ip
                );
            } else {
                jsonResponse.addProperty("message", "Không thể tạo người dùng. Tên đăng nhập đã tồn tại.");
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                logger.warn("User: {}, Failed to create user (tên tồn tại): {}", actorUsername, user.getUserName());
                UserLogsService.getInstance().logAction(
                        "WARN", actorUsername, roles, "Tạo user thất bại (trùng tên): " + user.getUserName(), ip
                );
            }

            response.getWriter().write(gson.toJson(jsonResponse));
        } catch (JsonSyntaxException e) {
            logger.error("User: {}, JSON không hợp lệ: {}", actorUsername, e.getMessage());
            UserLogsService.getInstance().logAction("ERROR", actorUsername, roles, "Gửi dữ liệu JSON lỗi", ip);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Dữ liệu JSON không hợp lệ\"}");
        } catch (IllegalArgumentException e) {
            logger.error("User: {}, Thiếu trường dữ liệu: {}", actorUsername, e.getMessage());
            UserLogsService.getInstance().logAction("ERROR", actorUsername, roles, "Thiếu trường dữ liệu khi tạo user", ip);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            logger.error("User: {}, Lỗi hệ thống khi tạo user: {}", actorUsername, e.getMessage(), e);
            UserLogsService.getInstance().logAction("FATAL", actorUsername, roles, "Lỗi hệ thống khi tạo user", ip);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Đã xảy ra lỗi trong quá trình xử lý yêu cầu: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = LoggerFactory.getLogger(ManagerUsers.class);
        // Lấy thông tin người thao tác
        HttpSession session = request.getSession(false);
        User authUser = (session != null) ? (User) session.getAttribute("auth") : null;
        String actorUsername = (authUser != null) ? authUser.getUserName() : "unknown";
        List<String> roles = (authUser != null) ? authUser.getRoles() : List.of();
        String ip = request.getRemoteAddr();

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

            // Parse JSON
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
            AUser user = gson.fromJson(json, AUser.class);

            // Kiểm tra các trường dữ liệu, đảm bảo không có giá trị null
            if (user.getId() == null || user.getUserName() == null) {
                throw new IllegalArgumentException("Missing required fields");
            }

            // Gọi service để cập nhật
            AUserService userService = new AUserService();
            boolean isUpdated = userService.updateUser(user, user.getUserName());

            // Logging hành động
            if (isUpdated) {
                logger.info("User: {}, Action: Updated user: {}", actorUsername, user.getUserName());
                UserLogsService.getInstance().logAction("INFO", actorUsername, roles, "Cập nhật thông tin user: " + user.getUserName(), ip);
            } else {
                logger.warn("User: {}, Failed to update user: {}", actorUsername, user.getUserName());
                UserLogsService.getInstance().logAction("WARN", actorUsername, roles, "Cập nhật thông tin user thất bại: " + user.getUserName(), ip);
            }

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
            logger.error("User: {}, JSON không hợp lệ: {}", actorUsername, e.getMessage());
            UserLogsService.getInstance().logAction("ERROR", actorUsername, roles, "Gửi dữ liệu JSON lỗi", ip);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Dữ liệu JSON không hợp lệ\"}");
        } catch (IllegalArgumentException e) {
            logger.error("User: {}, Thiếu trường dữ liệu: {}", actorUsername, e.getMessage());
            UserLogsService.getInstance().logAction("ERROR", actorUsername, roles, "Thiếu trường dữ liệu khi cập nhật user", ip);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            logger.error("User: {}, Lỗi hệ thống khi cập nhật user: {}", actorUsername, e.getMessage(), e);
            UserLogsService.getInstance().logAction("FATAL", actorUsername, roles, "Lỗi hệ thống khi cập nhật user", ip);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Đã xảy ra lỗi trong quá trình xử lý yêu cầu: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = LoggerFactory.getLogger(ManagerUsers.class);
        // Lấy thông tin người thao tác
        HttpSession session = request.getSession(false);
        User authUser = (session != null) ? (User) session.getAttribute("auth") : null;
        String actorUsername = (authUser != null) ? authUser.getUserName() : "unknown";
        List<String> roles = (authUser != null) ? authUser.getRoles() : List.of();
        String ip = request.getRemoteAddr();

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

            // Logging hành động
            if (isDeleted) {
                logger.info("User: {}, Action: Deleted user: {}", actorUsername, username);
                UserLogsService.getInstance().logAction("INFO", actorUsername, roles, "Xóa user: " + username, ip);
            } else {
                logger.warn("User: {}, Failed to delete user: {}", actorUsername, username);
                UserLogsService.getInstance().logAction("WARN", actorUsername, roles, "Xóa user thất bại: " + username, ip);
            }

            // Phản hồi
            if (isDeleted) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"Xóa mềm người dùng thành công\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"message\": \"Không tìm thấy hoặc không thể xóa người dùng\"}");
            }
        } catch (Exception e) {
            logger.error("User: {}, Lỗi hệ thống khi xóa user: {}", actorUsername, e.getMessage(), e);
            UserLogsService.getInstance().logAction("FATAL", actorUsername, roles, "Lỗi hệ thống khi xóa user", ip);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Có lỗi xảy ra: " + e.getMessage() + "\"}");
        }
    }
}