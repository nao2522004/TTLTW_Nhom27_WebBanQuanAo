package vn.edu.hcmuaf.fit.webbanquanao.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.admin.Mapper.ResourceMapper;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    public static final int EXECUTE = 1;
    public static final int WRITE = 2;
    public static final int READ = 4;

    // Các URL công khai không cần kiểm tra quyền
    private static final Set<String> PUBLIC_URLS = Set.of(
            "/forgotPassword", "/google-callback", "/google-login", "/logout", "/login", "/register", "/ResetPassword", "/verifyOTP",
            "/hello-servlet", "/homePage", "/navController", "/productDetail", "/productFilter", "/productPagination", "/productSearch"
    );

    // Các URL admin cần kiểm tra quyền
    private static final Set<String> ADMIN_URLS = Set.of("/admin.jsp");

    // Ánh xạ phương thức HTTP -> quyền truy cập
    private static final Map<String, Integer> METHOD_TO_PERMISSION = Map.of(
            "GET", READ,
            "POST", WRITE,
            "PUT", WRITE,
            "DELETE", EXECUTE
    );

    // Các phần mở rộng tệp tĩnh cần bỏ qua
    private static final Set<String> STATIC_EXTENSIONS = Set.of("css", "js", "jpg", "jpeg", "png", "gif", "ico", "woff", "woff2", "ttf", "svg", "map");

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        filterConfig.getServletContext().log("AuthorizationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String contextPath = httpRequest.getContextPath();
        String path = httpRequest.getRequestURI().substring(contextPath.length());
        String method = httpRequest.getMethod().toUpperCase();

        // Bỏ qua các URL công khai và các tài nguyên tĩnh
        if (isPublicUrl(path) || isStaticResource(path)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        User user = session != null ? (User) session.getAttribute("auth") : null;
        Map<String, Integer> permissions = session != null ?
                (Map<String, Integer>) session.getAttribute("permissions") : null;
        List<String> roles = session != null ?
                (List<String>) session.getAttribute("roles") : null;

        // Kiểm tra quyền cho admin URLs
        if (isAdminUrl(path)) {
            if (user == null) {
                redirectToLogin(httpRequest, httpResponse);
                return;
            }
            if (!roles.contains("ADMIN")) {
                showAccessDenied(httpRequest, httpResponse);
                return;
            }
        }

        // Kiểm tra quyền cho các URL khác
        if (user != null) {
            String resource = ResourceMapper.getResource(path);
            int requiredPermission = determineRequiredPermission(method);

            if (!hasPermission(permissions, resource, requiredPermission)) {
                showAccessDenied(httpRequest, httpResponse);
                return;
            }
        } else {
            redirectToLogin(httpRequest, httpResponse);
            return;
        }

        // Tiến hành chuỗi lọc tiếp theo
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        filterConfig.getServletContext().log("AuthorizationFilter destroyed");
    }

    // Kiểm tra URL công khai
    private boolean isPublicUrl(String path) {
        return PUBLIC_URLS.contains(path);
    }

    // Kiểm tra URL admin
    private boolean isAdminUrl(String path) {
        return ADMIN_URLS.contains(path);
    }

    // Kiểm tra tài nguyên tĩnh (CSS, JS, ảnh...)
    private boolean isStaticResource(String path) {
        int lastDot = path.lastIndexOf('.');
        if (lastDot == -1) return false; // Không có dấu "." => không phải file tĩnh
        String extension = path.substring(lastDot + 1).toLowerCase();
        return STATIC_EXTENSIONS.contains(extension);
    }

    // Xác định quyền cần thiết theo phương thức HTTP
    private int determineRequiredPermission(String method) {
        return METHOD_TO_PERMISSION.getOrDefault(method, READ);
    }

    // Kiểm tra quyền của người dùng
    private boolean hasPermission(Map<String, Integer> permissions, String resource, int requiredPermission) {
        if (permissions == null) return false;
        int resourcePermission = permissions.getOrDefault(resource, 0);
        return (resourcePermission & requiredPermission) == requiredPermission;
    }

    // Chuyển hướng đến trang đăng nhập nếu không có session hoặc quyền
    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectUrl = request.getContextPath() + "/login?redirect=" +
                URLEncoder.encode(request.getRequestURI(), "UTF-8");
        response.sendRedirect(redirectUrl);
    }

    // Hiển thị thông báo lỗi 403 (Forbidden)
    private void showAccessDenied(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Thêm thông báo vào request attribute để hiển thị trên trang
        request.setAttribute("errorMessage", "Bạn không có quyền hạn để truy cập trang này");

        // Chuyển hướng đến trang thông báo
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
}
