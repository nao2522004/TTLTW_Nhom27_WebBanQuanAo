//package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import vn.edu.hcmuaf.fit.webbanquanao.user.auth.service.RegisterEmailService;
//
//import java.io.IOException;
//@WebServlet(name = "RegisterEmail", value = "/RegisterEmail")
//public class RegisterEmailController extends HttpServlet {
//
//
//    private RegisterEmailService registerEmailService;
//
//    @Override
//    public void init() throws ServletException {
//        // Khởi tạo service khi servlet được tạo
//        registerEmailService = new RegisterEmailService();
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.getWriter().println("Truy cập không hợp lệ.");
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Lấy email từ form
//        String email = request.getParameter("email");
//
//        // Kiểm tra email không null hoặc rỗng
//        if (email == null || email.trim().isEmpty()) {
//            response.getWriter().println("Email không được để trống.");
//            return;
//        }
//
//        // Gọi dịch vụ để xử lý đăng ký email
//        String resultMessage = registerEmailService.registerEmail(email);
//
//        // Trả lại thông báo vào JSP
//        request.setAttribute("message", resultMessage);
//        request.getRequestDispatcher("home.jsp").forward(request, response); // Đảm bảo chuyển đến trang đúng
//    }
//}
