package vn.edu.hcmuaf.fit.webbanquanao.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.admin.Mapper.ResourceMapper;

import java.io.IOException;
import java.util.Map;

@WebFilter("/*")
public class LoginFilter implements Filter {
    private static final String[] PUBLIC_PATHS = {
            "/login", "/login.jsp",
            "/login-facebook", "/google-login",
            "/register", "/register.jsp",
            "/forgotPassword", "/forgot-password.jsp",
            "/ResetPassword", "/reset-password.jsp",
            "/verify", "/verify.jsp", "/verifyOTP", "/resend-otp",
            "/css/", "/js/", "/images/", "/assets/",
            "/homePage", "/productDetail", "/productSearch", "/productFilter", "/productPagination"
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

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        System.out.println("[LoginFilter] Requested path: " + path);

        // Nếu là tài nguyên công khai → cho phép
        if (isPublic(path)) {
            System.out.println("[LoginFilter] Public path, allow: " + path);
            chain.doFilter(request, response);
            return;
        }

        // Nếu chưa đăng nhập hoặc không có session "auth"
        if (session == null || session.getAttribute("auth") == null) {
            System.out.println("[LoginFilter] Không có session hoặc chưa đăng nhập, redirect về /login");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return;
        }

        // Đã đăng nhập
        User currentUser = (User) session.getAttribute("auth");

        // Kiểm tra trạng thái tài khoản
        if (currentUser.getStatus() == null || currentUser.getStatus() == 0) {
            System.out.println("[LoginFilter] Tài khoản chưa kích hoạt, redirect về /activate-account");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/activate-account.jsp");
            return;
        }

        // In roles và permissions
        System.out.println("[LoginFilter] User: " + currentUser.getUserName());
        System.out.println("[LoginFilter] Roles: " + currentUser.getRoles());
        System.out.println("[LoginFilter] Permissions: " + currentUser.getPermissions());

        // Kiểm tra quyền truy cập resource
        String resource = ResourceMapper.getResource(path);
        Integer permission = currentUser.getPermissions().get(resource);

        if (!"default".equals(resource)) {
            if (permission == null || permission < 1) { // Yêu cầu quyền >= 1 (READ)
                System.out.println("[LoginFilter] Không có quyền truy cập resource: " + resource + " → redirect /403.jsp");
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/403.jsp");
                return;
            }
        }

        // Cho phép truy cập
        System.out.println("[LoginFilter] Đã đăng nhập, tài khoản hợp lệ và có quyền, cho phép truy cập: " + path);
        chain.doFilter(request, response);
    }
}
