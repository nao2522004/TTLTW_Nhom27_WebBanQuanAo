package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUserRolePermission;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AUserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ManagerUserRolePermissions", value = "/admin/manager-userRolePermissions")
public class UserRolePermissionsApi extends HttpServlet {
    private AUserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new AUserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("userName");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (username != null && !username.isEmpty()) {
            Map<String, AUserRolePermission> rolePermMap = userService.getUserRoleByUsername(username);

            if (rolePermMap != null) {
                List<AUserRolePermission> list = new ArrayList<>(rolePermMap.values());
                Gson gson = new Gson();
                String json = gson.toJson(list);
                PrintWriter out = response.getWriter();
                out.print(json);
                out.flush();
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"message\": \"Không tìm thấy user\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Thiếu tham số username\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Lấy người dùng đang đăng nhập từ session
            HttpSession session = request.getSession(false);
            User currentUser = (User) session.getAttribute("auth");

            if (currentUser == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"message\": \"Bạn chưa đăng nhập\"}");
                return;
            }

            // ✅ Test: In ra userName trong session
            System.out.println("User dang dang nhap: " + currentUser.getUserName());

            // Đọc JSON từ body
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                jsonBuffer.append(line);
            }

            String jsonData = jsonBuffer.toString();

            // Parse JSON thành AUserRolePermission
            Gson gson = new Gson();
            AUserRolePermission updatedUser = gson.fromJson(jsonData, AUserRolePermission.class);

            if (updatedUser.getUserName() == null || updatedUser.getUserName().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Thiếu userName\"}");
                return;
            }

            // ✅ Test: In ra userName và danh sách quyền được gửi lên từ client
            System.out.println("User duoc cap nhap quyen: " + updatedUser.getUserName());
            System.out.println("Quyen duoc gui len: " + updatedUser.getRoles());

            // Gọi service để kiểm tra quyền và cập nhật
            boolean success = userService.updateUserRolesWithPermissionCheck(
                    currentUser,
                    updatedUser.getUserName(),
                    updatedUser.getRoles()
            );

            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"Cập nhật thành công\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("{\"message\": \"Bạn không có quyền thay đổi vai trò người dùng này\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Lỗi khi cập nhật thông tin\"}");
        }
    }

}
