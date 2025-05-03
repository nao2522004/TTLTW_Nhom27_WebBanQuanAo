package vn.edu.hcmuaf.fit.webbanquanao.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.hcmuaf.fit.webbanquanao.admin.Mapper.ResourceMapper;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);  // Logger của SLF4J

    public static final int EXECUTE = 1;
    public static final int WRITE = 2;
    public static final int READ = 4;

    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/login", "/login.jsp", "/login-facebook", "/google-login", "/register", "/register.jsp",
            "/forgotPassword", "/forgot-password.jsp", "/ResetPassword", "/reset-password.jsp",
            "/verify", "/verify.jsp", "/verifyOTP", "/resend-otp",
            "/css/", "/js/", "/images/", "/assets/", "/homePage", "/productDetail",
            "/productSearch", "/productFilter", "/productPagination"
    );

    private static final Set<String> ADMIN_PATHS = Set.of("/admin.jsp");

    private static final Set<String> STATIC_EXTENSIONS = Set.of(
            "css", "js", "jpg", "jpeg", "png", "gif", "ico", "woff", "woff2", "ttf", "svg", "map", "webp"
    );

    private static final Map<String, Integer> METHOD_TO_PERMISSION = Map.of(
            "GET", READ,
            "POST", WRITE,
            "PUT", WRITE,
            "DELETE", EXECUTE
    );

    private UserLogsService userLogsService;

    @Override
    public void init(FilterConfig filterConfig) {
        // Inject singleton của UserLogsService
        this.userLogsService = UserLogsService.getInstance();
        filterConfig.getServletContext().log("AuthorizationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        String method = httpRequest.getMethod().toUpperCase();
        String resource = ResourceMapper.getResource(path);

        // Bỏ qua các file tĩnh
        if (isStaticResource(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Public path → cho qua
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        User user = session != null ? (User) session.getAttribute("auth") : null;

        // Chưa đăng nhập
        if (user == null) {
            String redirectUrl = httpRequest.getContextPath() + "/login.jsp?redirect=" +
                    URLEncoder.encode(httpRequest.getRequestURI(), "UTF-8");
            httpResponse.sendRedirect(redirectUrl);

            logger.warn("Người dùng chưa đăng nhập từ IP: {} truy cập vào path: {}", httpRequest.getRemoteAddr(), path);
            return;
        }

        // Chưa kích hoạt tài khoản
        if (user.getStatus() == null || user.getStatus() == 0) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/activate-account.jsp");
            logger.warn("Tài khoản người dùng chưa được kích hoạt. Người dùng: {}, IP: {}", user.getUserName(), httpRequest.getRemoteAddr());
            return;
        }


        // Log thông tin user duy nhất 1 lần
        if (session.getAttribute("hasLoggedUserInfo") == null) {
            logger.info("Đăng nhập thành công cho người dùng: {}", user.getUserName());

            // Ghi log khi người dùng đã đăng nhập thành công
            userLogsService.logLoginSuccess(user.getUserName(), httpRequest.getRemoteAddr(), user.getRoles());

            session.setAttribute("hasLoggedUserInfo", true);
        }

        // Admin path → cần quyền ADMIN
        if (ADMIN_PATHS.contains(path) && (user.getRoles() == null ||
                !(user.getRoles().contains("ADMIN") || user.getRoles().contains("MANAGER")))) {
            httpRequest.setAttribute("errorMessage", "Bạn Không Có Quyền Truy Cập Vào Trang Này");

            // Ghi log khi người dùng không có quyền truy cập trang admin
            userLogsService.logUnauthorizedAccess(user.getUserName(), path, resource, READ, httpRequest.getRemoteAddr(), user.getRoles());

            logger.warn("Người dùng: {} không có quyền truy cập vào trang quản trị: {}. IP: {}", user.getUserName(), path, httpRequest.getRemoteAddr());

            httpRequest.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // Kiểm tra quyền truy cập vào resource
        int requiredPermission = METHOD_TO_PERMISSION.getOrDefault(method, READ);

        Integer userPermission = user.getPermissions() != null ? user.getPermissions().getOrDefault(resource, 0) : 0;
        if (!"default".equals(resource) && (userPermission & requiredPermission) != requiredPermission) {

            // Ghi log khi người dùng không có quyền truy cập trang admin
            userLogsService.logUnauthorizedAccess(user.getUserName(), path, resource, requiredPermission, httpRequest.getRemoteAddr(), user.getRoles());

            logger.warn("Người dùng: {} không có quyền truy cập vào resource: {}. Cần quyền: {}. IP: {}", user.getUserName(), resource, requiredPermission, httpRequest.getRemoteAddr());

            httpRequest.setAttribute("errorMessage", "Ban khong du quyen truy cap vao chuc nang nay");
            httpRequest.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // Log hệ thống (SLF4J + Logback)
        logger.info("User {} truy cập thành công vào {} -> {}", user.getUserName(), path, resource);

        // Log khi truy cập được phép
        userLogsService.logAccessGranted(user.getUserName(), path, resource, requiredPermission, httpRequest.getRemoteAddr(), user.getRoles());

        // Nếu hợp lệ → tiếp tục
        chain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        for (String publicPath : PUBLIC_PATHS) {
            if (path.contains(publicPath)) return true;
        }
        return false;
    }

    private boolean isStaticResource(String path) {
        int dotIndex = path.lastIndexOf('.');
        if (dotIndex == -1) return false;
        String ext = path.substring(dotIndex + 1).toLowerCase();
        return STATIC_EXTENSIONS.contains(ext);
    }

    @Override
    public void destroy() {
        System.out.println("AuthorizationFilter destroyed");
    }
}
