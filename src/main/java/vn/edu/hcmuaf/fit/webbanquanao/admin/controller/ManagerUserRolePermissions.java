package vn.edu.hcmuaf.fit.webbanquanao.admin.controller;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.admin.dao.AUserDao;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUserRolePermission;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AUserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ManagerUserRolePermissions", value = "/admin/manager-userRolePermissions")
public class ManagerUserRolePermissions extends HttpServlet {
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
            Map<String, AUserRolePermission> rolePermMap = userService.getUserRolePermissionByUsername(username);

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

//            // Gọi Service để cập nhật
//            userService.updateUserRolesAndPermissions(
//                    updatedUser.getUserName(),
//                    updatedUser.getRoles(),
//                    updatedUser.getPermissions()
//            );

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"Cập nhật thành công\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Lỗi khi cập nhật thông tin\"}");
        }
    }

}
