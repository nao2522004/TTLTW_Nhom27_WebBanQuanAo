package vn.edu.hcmuaf.fit.webbanquanao.user.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.user.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/updateProfileServlet")
public class UpdateProfileController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ form
        String name = request.getParameter("name");

        // Lấy thông tin người dùng hiện tại từ session
        User user = (User) request.getSession().getAttribute("auth");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        int phone = 0;
        try {
            String phoneInput = request.getParameter("phone");

            long phoneLong = Long.parseLong(phoneInput);
            if (phoneLong > Integer.MAX_VALUE || phoneLong < Integer.MIN_VALUE) {
                throw new IllegalArgumentException("Giá trị số điện thoại vượt quá phạm vi kiểu int.");
            }
            phone = (int) phoneLong;

            user.setPhone(phone);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Số điện thoại không hợp lệ.");
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }

        // Cập nhật thông tin người dùng
        String fullName = request.getParameter("name");
        String[] nameParts = fullName.trim().split("\\s+");

        String firstName = nameParts[nameParts.length - 1];
        String lastName = String.join(" ", Arrays.copyOfRange(nameParts, 0, nameParts.length - 1));

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);

//        // Lưu vào cơ sở dữ liệu
//        UserDao userDao = new UserDao();
//        boolean updateSuccess = userDao.updateUser(user);
//
//        if (updateSuccess) {
//            // Cập nhật lại thông tin người dùng trong session
//            request.getSession().setAttribute("auth", user);
//            response.sendRedirect("user.jsp");
//        } else {
//            response.getWriter().write("Cập nhật thất bại!");
//        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            String userName = jsonObject.has("userName") ? jsonObject.get("userName").getAsString() : null;
            String currentPassword = jsonObject.has("currentPassword") ? jsonObject.get("currentPassword").getAsString() : null;
            String newPassword = jsonObject.has("newPassword") ? jsonObject.get("newPassword").getAsString() : null;

            // Kiểm tra dữ liệu đầu vào
            if (userName == null || currentPassword == null || newPassword == null) {
                throw new IllegalArgumentException("Missing required fields: userName, currentPassword, or newPassword");
            }

            // Gọi service để đổi mật khẩu
            UserService userService = new UserService();
            boolean isUpdated = userService.changePasswordUser(userName, currentPassword, newPassword);

            // Phản hồi
            JsonObject jsonResponse = new JsonObject();
            if (isUpdated) {
                jsonResponse.addProperty("message", "Đổi mật khẩu thành công");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                jsonResponse.addProperty("message", "User không tồn tại hoặc mật khẩu cũ không đúng");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            response.getWriter().write(jsonResponse.toString());

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Invalid JSON format\"}");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"" + e.getMessage() + "\"}");
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
