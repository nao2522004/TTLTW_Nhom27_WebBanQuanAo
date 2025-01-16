package vn.edu.hcmuaf.fit.webbanquanao.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.model.User;

import java.io.IOException;

@WebServlet(name = "SignupController", value = "/signupController")
public class SignupController extends HttpServlet {

    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String gmail = request.getParameter("gmailRe");
        String password = request.getParameter("passwordRe");
        String confirmPassword = request.getParameter("confirmPasswordRe");

        HttpSession session = request.getSession();
        if (!password.equals(confirmPassword)) {
            session.setAttribute("error", "Mật khẩu xác minh không khớp");
            response.sendRedirect("login.jsp#signup-form");
            return;
        }

        if (userDao.isEmailExist(gmail)) {
            session.setAttribute("error", "Email đã được sử dụng");
            response.sendRedirect("login.jsp#signup-form");
            return;
        }

        User newUser = new User("", password, firstName, lastName ,gmail, "", "", 0, 2);
        boolean isSuccess = userDao.registerUser(newUser);

        if (isSuccess) {
            // Đăng nhập ngay sau khi đăng ký thành công
            session.setAttribute("auth", newUser); // Tự động đăng nhập
            response.sendRedirect("home.jsp");
        } else {
            session.setAttribute("error", "Đăng ký không thành công. Vui lòng thử lại.");
            response.sendRedirect("login.jsp#signup-form");
        }
    }
}
