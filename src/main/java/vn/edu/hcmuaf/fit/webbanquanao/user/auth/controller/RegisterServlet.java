//package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import vn.edu.hcmuaf.fit.webbanquanao.user.auth.service.EmailService;
//import vn.edu.hcmuaf.fit.webbanquanao.user.auth.model.OTPStorage;
//import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;
//import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
//import org.mindrot.jbcrypt.BCrypt;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Random;
//
//@WebServlet("/register")
//public class RegisterServlet extends HttpServlet {
//    private UserDao userDao;
//
//    @Override
//    public void init() {
//        userDao = new UserDao();
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String username = request.getParameter("username");
//        String firstName = request.getParameter("firstName");
//        String lastName = request.getParameter("lastName");
//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//        HttpSession session = request.getSession();
//
//        // Kiểm tra dữ liệu nhập vào
//        if (username == null || username.trim().isEmpty()) {
//            session.setAttribute("error", "Tên tài khoản không hợp lệ!");
//            response.sendRedirect("login.jsp#signup-form");
//            return;
//        }
//        if (firstName == null || firstName.trim().isEmpty()) {
//            session.setAttribute("error", "Họ không được để trống!");
//            response.sendRedirect("login.jsp#signup-form");
//            return;
//        }
//        if (lastName == null || lastName.trim().isEmpty()) {
//            session.setAttribute("error", "Tên không được để trống!");
//            response.sendRedirect("login.jsp#signup-form");
//            return;
//        }
//        if (email == null || email.trim().isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
//            session.setAttribute("error", "Email không hợp lệ!");
//            response.sendRedirect("login.jsp#signup-form");
//            return;
//        }
//
//        // Lấy tên role "USER"
//        String roleName = userDao.getRoleNameById("USER");
//        if (roleName == null) {
//            session.setAttribute("error", "Không tìm thấy role 'USER'!");
//            response.sendRedirect("login.jsp#signup-form");
//            return;
//        }
//
//        // Mã hóa mật khẩu
//        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
//
//        // Tạo và lưu OTP
//        String otp = generateOTP();
//        OTPStorage.storeOTP(email, otp);
//        EmailService.sendEmail(email, "🔒 Xác thực tài khoản - Mã OTP của bạn", otp);
//
//        // Tạo user tạm thời để lưu session
//        User tempUser = new User(username, firstName, lastName, email, hashedPassword);
//        ArrayList<String> roles = new ArrayList<>();
//        roles.add(roleName);
//        tempUser.setRoleName(roles); // Gán danh sách vai trò
//
//        session.setAttribute("tempUser", tempUser);
//
//        // Chuyển hướng sang trang xác thực OTP
//        response.sendRedirect("verify.jsp");
//    }
//
//    private String generateOTP() {
//        Random random = new Random();
//        return String.format("%06d", random.nextInt(999999));
//    }
//}
//
