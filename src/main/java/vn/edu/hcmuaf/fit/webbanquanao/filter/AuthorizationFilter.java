package vn.edu.hcmuaf.fit.webbanquanao.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebFilter("/*") // Áp dụng cho toàn bộ request
public class AuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();

        Object userObj = session.getAttribute("auth");
        Map<String, Integer> userPermissions = (Map<String, Integer>) session.getAttribute("permissions");

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String method = httpRequest.getMethod().toUpperCase();
        String resource = extractResource(requestURI, contextPath);

        // Nếu là trang công khai -> Cho phép truy cập luôn
        if (isPublicPage(requestURI, contextPath)) {
            chain.doFilter(request, response);
            return;
        }

        // Nếu chưa đăng nhập và vào trang admin -> Chặn lại
        if (userObj == null && isAdminPage(requestURI, contextPath)) {
            showAlertAndRedirect(httpResponse, "Thông báo từ AuthorizationFilter: Bạn chưa đăng nhập!", contextPath + "/login.jsp");
            return;
        }

        // Nếu đã đăng nhập, kiểm tra quyền
        if (userObj != null) {
            boolean isAdmin = session.getAttribute("roles") != null && session.getAttribute("roles").toString().contains("Admin");

            // Nếu truy cập admin nhưng không phải admin -> Chuyển về homePage
            if (isAdminPage(requestURI, contextPath) && !isAdmin) {
                showAlertAndRedirect(httpResponse, "Thông báo từ AuthorizationFilter: Bạn không có quyền truy cập trang admin!", contextPath + "/homePage");
                return;
            }

            // Xác định quyền cần kiểm tra
            int requiredPermission = switch (method) {
                case "GET" -> 4; // READ
                case "POST" -> 1; // RUN
                case "PUT", "DELETE" -> 2; // WRITE
                default -> 4;
            };

            // Kiểm tra quyền với resource
            if (!isAdmin && !hasPermission(userPermissions, resource, requiredPermission)) {
                showErrorMessage(httpRequest, httpResponse, "Thông báo từ AuthorizationFilter: Bạn không có quyền thực hiện thao tác này.");
                return;
            }
        }

        // Nếu đủ quyền thì tiếp tục
        chain.doFilter(request, response);
    }

    // Kiểm tra quyền dựa trên bitwise
    private boolean hasPermission(Map<String, Integer> permissions, String resource, int requiredPermission) {
        if (permissions == null || !permissions.containsKey(resource)) {
            return false;
        }
        int permission = permissions.get(resource);
        return (permission & requiredPermission) == requiredPermission;
    }

    // Trích xuất resource từ URL
    private String extractResource(String requestURI, String contextPath) {
        String path = requestURI.replaceFirst(contextPath, "");
        String[] parts = path.split("/");
        return (parts.length > 1) ? parts[1] : "homePage";
    }

    // Kiểm tra nếu URL là trang admin
    private boolean isAdminPage(String requestURI, String contextPath) {
        return requestURI.startsWith(contextPath + "/admin.jsp");
    }

    // Xác định nếu URL là trang công khai
    private boolean isPublicPage(String requestURI, String contextPath) {
        return requestURI.equals(contextPath + "/") ||
                requestURI.startsWith(contextPath + "/homePage") ||
                requestURI.startsWith(contextPath + "/login.jsp");
    }

    // Hiển thị thông báo và chuyển hướng trang
    private void showAlertAndRedirect(HttpServletResponse response, String message, String redirectURL) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<script>alert('" + message + "'); window.location.href='" + redirectURL + "';</script>");
    }

    // Hiển thị thông báo lỗi ngay trên giao diện mà không chuyển trang
    private void showErrorMessage(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
    }
}