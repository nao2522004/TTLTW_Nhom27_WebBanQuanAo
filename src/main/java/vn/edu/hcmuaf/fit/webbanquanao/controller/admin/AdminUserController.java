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
                response.getWriter().write("{\"message\": \"User not found\"}");
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
        // Lấy thông tin từ request
        String userId = request.getParameter("id");
        String username = request.getParameter("username");
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");
        String email = request.getParameter("email");
        String avatar = request.getParameter("avatar");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String createdDate = request.getParameter("createdDate");
        String status = request.getParameter("status");
        String role = request.getParameter("role");

        // Tạo đối tượng User từ thông tin request
        User updatedUser = new User();
        updatedUser.setId(Integer.parseInt(userId));
        updatedUser.setUserName(username);
        updatedUser.setLastName(lastName);
        updatedUser.setFirstName(firstName);
        updatedUser.setEmail(email);
        updatedUser.setAvatar(avatar);
        updatedUser.setAddress(address);
        updatedUser.setPhone(Integer.valueOf(phone));
        updatedUser.setCreatedAt(LocalDateTime.parse(createdDate)); // Giả sử bạn xử lý định dạng chuỗi thành LocalDateTime trong User
        updatedUser.setStatus(status.equals("active") ? 1 : 0); // Mapping status
        updatedUser.setRoleId(role.equals("admin") ? 1 : 2); // Mapping role

        // Gọi UserService để cập nhật người dùng
        UserService userService = new UserService();
        boolean isUpdated = userService.updateUser(updatedUser, username);

        // Thiết lập phản hồi JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        if (isUpdated) {
            out.print("{\"message\": \"User updated successfully\"}");
        } else {
            out.print("{\"message\": \"User update failed\"}");
        }
        out.flush();
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }


}
