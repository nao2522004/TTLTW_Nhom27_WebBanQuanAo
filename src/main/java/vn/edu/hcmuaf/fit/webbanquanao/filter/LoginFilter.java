package vn.edu.hcmuaf.fit.webbanquanao.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    private static final String[] PUBLIC_PATHS = {
            "/login", "/login.jsp",
            "/login-facebook", "/google-login",
            "/register", "/register.jsp",
            "/forgotPassword", "/forgot-password.jsp",
            "/ResetPassword", "/reset-password.jsp",
            "/verify", "/verify.jsp", "/verifyOTP", "/resend-otp",
            "/css/", "/js/", "/images/", "/assets/"
    };

    private boolean isPublic(String path) {
        for (String publicPath : PUBLIC_PATHS) {
            if (path.contains(publicPath)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false); // không tạo session mới nếu chưa có

        // Lấy đường dẫn URI
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        System.out.println("[LoginFilter] Requested path: " + path);

        // Nếu là tài nguyên công khai → cho phép
        if (isPublic(path)) {
            System.out.println("[LoginFilter] Public path, allow: " + path);
            chain.doFilter(request, response);
            return;
        }

        // Nếu chưa đăng nhập (không có session hoặc không có "auth") → chuyển hướng về login
        if (session == null || session.getAttribute("auth") == null) {
            System.out.println("[LoginFilter] Không có session hoặc chưa đăng nhập, redirect về /login");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return;
        }

        // Nếu đã đăng nhập → tiếp tục
        System.out.println("[LoginFilter] Đã đăng nhập, cho phép truy cập: " + path);
        chain.doFilter(request, response);
    }
}
