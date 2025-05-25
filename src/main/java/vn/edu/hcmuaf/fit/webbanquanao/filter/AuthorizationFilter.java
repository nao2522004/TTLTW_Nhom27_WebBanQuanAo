package vn.edu.hcmuaf.fit.webbanquanao.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.admin.Mapper.ResourceMapper;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    public static final Set<String> PUBLIC_PATHS = Set.of(
            "/login", "/login.jsp", "/login-facebook", "/google-login", "/google-callback",
            "/register", "/register.jsp", "/forgotPassword", "/forgot-password.jsp",
            "/ResetPassword", "/reset-password.jsp", "/verify", "/verify.jsp",
            "/verifyOTP", "/resend-otp", "/homePage", "/productDetail",
            "/productSearch", "/productFilter", "/productPagination",
            "/facebook-callback", "/sendChangePasswordEmail", "/change-password.jsp",
            "/change-password"
    );

    public static final Set<String> STATIC_EXTENSIONS = Set.of(
            "css", "js", "jpg", "jpeg", "png", "gif", "ico",
            "woff", "woff2", "ttf", "svg", "map", "webp"
    );

    private static final Set<String> ADMIN_PATHS = Set.of("/admin.jsp");

    private static final Map<String, Integer> METHOD_PERM = Map.of(
            "GET", 4,
            "POST", 2,
            "PUT", 2,
            "DELETE", 1
    );

    private UserLogsService logService;

    @Override
    public void init(FilterConfig config) {
        logService = UserLogsService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        try {
            String requestPath = request.getRequestURI().substring(request.getContextPath().length());

            if (isPublicOrStatic(requestPath)) {
                chain.doFilter(req, res);
                return;
            }

            HttpSession session = request.getSession(false);
            User user = session != null ? (User) session.getAttribute("auth") : null;
            String ip = request.getRemoteAddr();

            if (user == null) {
                logService.logAnonymousAccessAttempt(requestPath, ip);
                redirectToLogin(request, response);
                return;
            }

            if (session.getAttribute("hasLoggedUserInfo") == null) {
                logService.logLoginSuccess(user.getUserName(), ip, user.getRoles());
                session.setAttribute("hasLoggedUserInfo", true);
            }

            if (ADMIN_PATHS.contains(requestPath) && !hasAnyRole(user, Set.of("ADMIN", "MANAGER"))) {
                logService.logUnauthorizedAccess(user.getUserName(), requestPath, ResourceMapper.getResource(requestPath), METHOD_PERM.get("GET"), ip, user.getRoles());
                forwardError(request, response, "Bạn không có quyền truy cập trang này");
                return;
            }

            int required = METHOD_PERM.getOrDefault(request.getMethod(), 4);
            int userPerm = Optional.ofNullable(user.getPermissions()).orElse(Map.of())
                    .getOrDefault(ResourceMapper.getResource(requestPath), 0);
            if (!ResourceMapper.getResource(requestPath).equals("default") && (userPerm & required) != required) {
                logService.logUnauthorizedAccess(user.getUserName(), requestPath, ResourceMapper.getResource(requestPath), required, ip, user.getRoles());
                forwardError(request, response, "Bạn không đủ quyền truy cập chức năng này");
                return;
            }

            if (!isAjax(request)
                    && !requestPath.equals(session.getAttribute("lastLoggedPath"))
                    && !requestPath.startsWith("/admin/api/")) {
                logService.logAccessGranted(user.getUserName(), requestPath, ResourceMapper.getResource(requestPath), required, ip, user.getRoles());
                session.setAttribute("lastLoggedPath", requestPath);
            }

            chain.doFilter(req, res);
        } catch (Exception ex) {
            ex.printStackTrace();
            forwardError((HttpServletRequest) req, (HttpServletResponse) res, "Đã xảy ra lỗi máy chủ. Vui lòng thử lại sau.");
        }
    }

    @Override
    public void destroy() {}

    private boolean isPublicOrStatic(String path) {
        // Nếu path trùng hoặc bắt đầu với public path + '/' hoặc public path + '?' thì coi là public
        if (PUBLIC_PATHS.stream().anyMatch(p ->
                path.equals(p)
                        || path.startsWith(p + "/")
                        || path.startsWith(p + "?"))) {
            return true;
        }

        int idx = path.lastIndexOf('.');
        return idx >= 0 && STATIC_EXTENSIONS.contains(path.substring(idx + 1).toLowerCase());
    }

    private void redirectToLogin(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String uri = URLEncoder.encode(req.getRequestURI(), StandardCharsets.UTF_8);
        res.sendRedirect(req.getContextPath() + "/login.jsp?redirect=" + uri);
    }

    private void forwardError(HttpServletRequest req, HttpServletResponse res, String msg)
            throws ServletException, IOException {
        req.setAttribute("errorMessage", msg);
        req.getRequestDispatcher("/error.jsp").forward(req, res);
    }

    private boolean isAjax(HttpServletRequest req) {
        return "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));
    }

    private boolean hasAnyRole(User user, Set<String> roles) {
        return Optional.ofNullable(user.getRoles()).orElse(List.of())
                .stream().anyMatch(roles::contains);
    }
}
