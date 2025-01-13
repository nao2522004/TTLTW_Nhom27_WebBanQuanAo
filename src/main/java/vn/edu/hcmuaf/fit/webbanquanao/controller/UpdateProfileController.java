package vn.edu.hcmuaf.fit.webbanquanao.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.dao.adminDAO.AUserDao;
import vn.edu.hcmuaf.fit.webbanquanao.model.User;

import java.io.IOException;
import java.util.Arrays;

@WebServlet("/updateProfileServlet")
public class UpdateProfileController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ form
        String name = request.getParameter("name");
        Integer phone = Integer.valueOf(request.getParameter("phone"));


        // Lấy thông tin người dùng hiện tại từ session
        User user = (User) request.getSession().getAttribute("auth");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Cập nhật thông tin người dùng
        String fullName = request.getParameter("name");
        String[] nameParts = fullName.trim().split("\\s+");

        // Kiểm tra xem có ít nhất hai từ không
        String firstName = nameParts[nameParts.length - 1];
        String lastName = String.join(" ", Arrays.copyOfRange(nameParts, 0, nameParts.length - 1));

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);


        // Lưu vào cơ sở dữ liệu
        AUserDao userDao = new AUserDao();
        boolean updateSuccess = userDao.updateUser(user);

        if (updateSuccess) {
            // Cập nhật lại thông tin người dùng trong session
            request.getSession().setAttribute("auth", user);
            response.sendRedirect("user.jsp");
        } else {
            response.getWriter().write("Cập nhật thất bại!");
        }
    }
}
