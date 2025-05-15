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

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

    public static final int EXECUTE = 1;
    public static final int WRITE   = 2;
    public static final int READ    = 4;

    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/login", "/login.jsp", "/login-facebook", "/google-login", "/google-callback",
            "/register", "/register.jsp", "/forgotPassword", "/forgot-password.jsp",
            "/ResetPassword", "/reset-password.jsp", "/verify", "/verify.jsp",
            "/verifyOTP", "/resend-otp", "/homePage", "/productDetail",
            "/productSearch", "/productFilter", "/productPagination",
            "/facebook-callback"
    );

    private static final Set<String> STATIC_FOLDERS = Set.of(
            "/css/", "/js/", "/images/", "/assets/"
    );

    private static final Set<String> STATIC_EXTENSIONS = Set.of(
            "css", "js", "jpg", "jpeg", "png", "gif", "ico",
            "woff", "woff2", "ttf", "svg", "map", "webp"
    );

    private static final Set<String> ADMIN_PATHS = Set.of(
            "/admin.jsp"
    );

    private static final Map<String, Integer> METHOD_TO_PERMISSION = Map.of(
            "GET",    READ,
            "POST",   WRITE,
            "PUT",    WRITE,
            "DELETE", EXECUTE
    );

    private UserLogsService userLogsService;

    @Override
    public void init(FilterConfig filterConfig) {
        userLogsService = UserLogsService.getInstance();
        filterConfig.getServletContext().log("AuthorizationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  httpRequest  = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path   = httpRequest.getRequestURI()
                .substring(httpRequest.getContextPath().length());
        String method = httpRequest.getMethod().toUpperCase();
        String resource = ResourceMapper.getResource(path);

        // Bypass static and public
        if (isStaticResource(path) || isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Xác thực session
        HttpSession session = httpRequest.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("auth") : null;

        if (user == null) {
            String redirect = httpRequest.getContextPath() + "/login.jsp?redirect=" +
                    URLEncoder.encode(httpRequest.getRequestURI(), "UTF-8");
            httpResponse.sendRedirect(redirect);
            logger.warn("Unauthenticated access attempt from IP {} to {}", httpRequest.getRemoteAddr(), path);
            return;
        }

        session = httpRequest.getSession(); // ensure session exists

        boolean ajax = isAjax(httpRequest);
        boolean isApiRequest = path.startsWith("/admin/api/");

        // Log đăng nhập lần đầu sau khi thành công
        if (session.getAttribute("hasLoggedUserInfo") == null) {
            logger.info("Login success: {} from IP {}", user.getUserName(), httpRequest.getRemoteAddr());
            userLogsService.logLoginSuccess(user.getUserName(), httpRequest.getRemoteAddr(), user.getRoles());
            session.setAttribute("hasLoggedUserInfo", true);
        }

        // Kiểm quyền admin
        if (ADMIN_PATHS.contains(path) && !hasRole(user, Set.of("ADMIN", "MANAGER"))) {
            httpRequest.setAttribute("errorMessage", "Bạn không có quyền truy cập trang này");
            userLogsService.logUnauthorizedAccess(user.getUserName(), path, resource, READ,
                    httpRequest.getRemoteAddr(), user.getRoles());
            logger.warn("Unauthorized admin access by {} to {}", user.getUserName(), path);
            httpRequest.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // Kiểm permissions theo phương thức
        int required = METHOD_TO_PERMISSION.getOrDefault(method, READ);
        int userPerm = (user.getPermissions() != null)
                ? user.getPermissions().getOrDefault(resource, 0)
                : 0;

        if (!"default".equals(resource) && (userPerm & required) != required) {
            userLogsService.logUnauthorizedAccess(user.getUserName(), path, resource, required,
                    httpRequest.getRemoteAddr(), user.getRoles());
            logger.warn("Access denied for {} on {} (perm: {})", user.getUserName(), resource, required);
            httpRequest.setAttribute("errorMessage", "Bạn không đủ quyền truy cập chức năng này");
            httpRequest.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // Log view page once per path
        String lastPath = (String) session.getAttribute("lastLoggedPath");
        boolean firstVisit = !path.equals(lastPath);

        if (!ajax && firstVisit && !isApiRequest) {
            logger.info("User {} access {} -> {}", user.getUserName(), path, resource);
            userLogsService.logAccessGranted(user.getUserName(), path, resource, required,
                    httpRequest.getRemoteAddr(), user.getRoles());
            session.setAttribute("lastLoggedPath", path);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup if needed
    }

    private boolean isPublicPath(String path) {
        for (String p : PUBLIC_PATHS) {
            if (path.equals(p) || path.startsWith(p + "/")) {
                return true;
            }
        }
        for (String f : STATIC_FOLDERS) {
            if (path.startsWith(f)) {
                return true;
            }
        }
        return false;
    }

    private boolean isStaticResource(String path) {
        int idx = path.lastIndexOf('.');
        if (idx == -1) return false;
        String ext = path.substring(idx + 1).toLowerCase();
        return STATIC_EXTENSIONS.contains(ext);
    }

    private boolean isAjax(HttpServletRequest req) {
        return "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));
    }

    private boolean hasRole(User user, Set<String> roles) {
        if (user.getRoles() == null) return false;
        for (String r : roles) {
            if (user.getRoles().contains(r)) return true;
        }
        return false;
    }
}
