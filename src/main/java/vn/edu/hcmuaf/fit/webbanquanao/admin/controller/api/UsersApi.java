package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api;

import com.google.gson.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUser;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AUserService;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@WebServlet(name = "UsersApi", urlPatterns = "/admin/api/users/*")
public class UsersApi extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AUserService userService = new AUserService();
    private final UserLogsService logService = UserLogsService.getInstance();
    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareResponse(resp);
        ApiContext ctx = new ApiContext(req);
        String username = extractId(req.getPathInfo());

        if (username == null) {
            List<AUser> users = new ArrayList<>(userService.showUser().values());
            if (!Boolean.TRUE.equals(ctx.session.getAttribute("viewAllUsers"))) {
                logService.logAccessGranted(ctx.username, req.getRequestURI(), "User", ctx.permissions, ctx.ip, ctx.roles);
                ctx.session.setAttribute("viewAllUsers", Boolean.TRUE);
            }
            writeJson(resp, users);
        } else {
            AUser user = userService.getUserByUsername(username);
            if (user != null) {
//                logService.logAccessGranted(ctx.username, req.getRequestURI(), "User", ctx.permissions, ctx.ip, ctx.roles);
                writeJson(resp, user);
            } else {
                logService.logCustom(ctx.username, "WARN", "User not found: " + username, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy user");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareResponse(resp);
        ApiContext ctx = new ApiContext(req);
        try {
            AUser user = gson.fromJson(readBody(req), AUser.class);
            validateCreate(user);

            Map<String, AUser> users = userService.showUser();
            if (users.containsKey(user.getUserName())) {
                logService.logCreateEntity(ctx.username, "User", user.getUserName(), ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_CONFLICT, "User đã tồn tại");
            } else {
                userService.addUser(user);
                logService.logCreateEntity(ctx.username, "User", user.getUserName(), ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_CREATED, "Tạo user thành công");
            }
        } catch (JsonSyntaxException | IllegalArgumentException e) {
            logService.logCustom(ctx.username, "ERROR", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi tạo user");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareResponse(resp);
        ApiContext ctx = new ApiContext(req);
        String username = extractId(req.getPathInfo());
        if (username == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu username trong URL");
            return;
        }
        try {
            AUser user = gson.fromJson(readBody(req), AUser.class);
            validateUpdate(user);
            boolean updated = userService.updateUser(user, username);
            if (updated) {
                logService.logUpdateEntity(ctx.username, "User", username, ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_OK, "Cập nhật user thành công");
            } else {
                logService.logCustom(ctx.username, "WARN", "Update failed: " + username, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy user");
            }
        } catch (JsonSyntaxException | IllegalArgumentException e) {
            logService.logCustom(ctx.username, "ERROR", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi cập nhật user");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareResponse(resp);
        ApiContext ctx = new ApiContext(req);
        String username = extractId(req.getPathInfo());
        if (username == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu username trong URL");
            return;
        }
        try {
            boolean deleted = userService.deleteUser(username);
            if (deleted) {
                logService.logDeleteEntity(ctx.username, "User", username, ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_OK, "Xóa user thành công");
            } else {
                logService.logCustom(ctx.username, "ERROR", "Delete failed: " + username, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy user");
            }
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi xóa user");
        }
    }

    // Validation methods
    private void validateCreate(AUser u) {
        if (u.getUserName() == null || u.getPassWord() == null) {
            throw new IllegalArgumentException("Thiếu dữ liệu bắt buộc khi tạo User");
        }
    }

    private void validateUpdate(AUser u) {
        if (u.getUserName() == null) {
            throw new IllegalArgumentException("Thiếu dữ liệu bắt buộc khi cập nhật User");
        }
    }

    // Utility methods
    private void prepareResponse(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    private String extractId(String pathInfo) {
        return (pathInfo == null || "/".equals(pathInfo)) ? null : pathInfo.substring(1);
    }

    private String readBody(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
        }
        return sb.toString();
    }

    private <T> void writeJson(HttpServletResponse resp, T data) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(data));
    }

    private void sendError(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);
        gson.toJson(Map.of("message", message), resp.getWriter());
    }

    private void sendSuccess(HttpServletResponse resp, int status, String message) throws IOException {
        sendError(resp, status, message);
    }

    // Context holder
    private static class ApiContext {
        final String username;
        final Integer permissions;
        final List<String> roles;
        final String ip;
        final HttpSession session;

        ApiContext(HttpServletRequest req) {
            this.session = req.getSession();
            User user = (User) session.getAttribute("auth");
            this.username = (user != null) ? user.getUserName() : "anonymous";
            this.roles = (user != null) ? user.getRoles() : List.of();
            this.permissions = (user != null) ? user.getPermissions().get("User") : 0;
            this.ip = req.getRemoteAddr();
            if (session.getAttribute("viewAllUsers") == null) {
                session.setAttribute("viewAllUsers", Boolean.FALSE);
            }
        }
    }
}
