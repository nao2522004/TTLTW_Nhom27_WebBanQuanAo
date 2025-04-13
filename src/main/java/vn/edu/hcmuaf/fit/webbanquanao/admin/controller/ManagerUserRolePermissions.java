package vn.edu.hcmuaf.fit.webbanquanao.admin.controller;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
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
}
