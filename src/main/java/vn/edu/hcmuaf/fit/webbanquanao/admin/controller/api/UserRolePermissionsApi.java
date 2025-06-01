package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUserRolePermission;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AUserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ManagerUserRolePermissions", value = "/admin/api/roleUser/*")
public class UserRolePermissionsApi extends HttpServlet {
    private AUserService userService;
    private final UserLogsService logService = UserLogsService.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new AUserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ApiContext ctx = new ApiContext(request);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo(); // ví dụ: /nguyenmanh
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.length() <= 1) {
            logService.logCustom(ctx.username, "ERROR", "Thiếu username trong path", ctx.ip, ctx.roles);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Thiếu username trong đường dẫn\"}");
            return;
        }

        String username = pathInfo.substring(1); // bỏ dấu '/'

        Map<String, AUserRolePermission> rolePermMap = userService.getUserRoleByUsername(username);
        if (rolePermMap != null) {
            List<AUserRolePermission> list = new ArrayList<>(rolePermMap.values());
            String json = new Gson().toJson(list);
            response.getWriter().print(json);
            response.getWriter().flush();

//            logService.logAccessGranted(ctx.username, request.getRequestURI(), "UserRolePermission", ctx.permissions, ctx.ip, ctx.roles);
        } else {
            logService.logCustom(ctx.username, "WARN", "Không tìm thấy user: " + username, ctx.ip, ctx.roles);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"message\": \"Không tìm thấy user\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        // Giữ nguyên logic trống nếu chưa dùng
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ApiContext ctx = new ApiContext(request);

        try {
            HttpSession session = request.getSession(false);
            User currentUser = (User) session.getAttribute("auth");

            if (currentUser == null) {
                logService.logCustom("anonymous", "ERROR", "Chưa đăng nhập khi PUT roleUser", ctx.ip, List.of());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"message\": \"Bạn chưa đăng nhập\"}");
                return;
            }

            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                jsonBuffer.append(line);
            }

            String jsonData = jsonBuffer.toString();
            Gson gson = new Gson();
            AUserRolePermission updatedUser = gson.fromJson(jsonData, AUserRolePermission.class);

            // 📝 Ghi log chi tiết dữ liệu được gửi lên
            logService.logCustom(currentUser.getUserName(), "INFO",
                    "Cập nhật quyền cho user: " + updatedUser.getUserName()
                            + ", Quyền được gửi lên: " + updatedUser.getRoles(),
                    ctx.ip, ctx.roles);

            if (updatedUser.getUserName() == null || updatedUser.getUserName().isEmpty()) {
                logService.logCustom(currentUser.getUserName(), "ERROR", "Thiếu userName trong PUT roleUser", ctx.ip, ctx.roles);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Thiếu userName\"}");
                return;
            }

            boolean success = userService.updateUserRolesWithPermissionCheck(
                    currentUser,
                    updatedUser.getUserName(),
                    updatedUser.getRoles()
            );

            if (success) {
                logService.logUpdateEntity(currentUser.getUserName(), "UserRolePermission", updatedUser.getUserName(), ctx.ip, ctx.roles);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"Cập nhật thành công\"}");
            } else {
                logService.logCustom(currentUser.getUserName(), "WARN",
                        "Không có quyền cập nhật roles cho user: " + updatedUser.getUserName(),
                        ctx.ip, ctx.roles);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("{\"message\": \"Bạn không có quyền thay đổi vai trò người dùng này\"}");
            }

        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", "Lỗi server khi PUT roleUser: " + e.getMessage(), ctx.ip, ctx.roles);
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Lỗi khi cập nhật thông tin\"}");
        }
    }



    // Context holder giống UsersApi
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
            this.permissions = (user != null && user.getPermissions() != null)
                    ? user.getPermissions().getOrDefault("UserRolePermission", 0)
                    : 0;
            this.ip = req.getRemoteAddr();
        }
    }
}

