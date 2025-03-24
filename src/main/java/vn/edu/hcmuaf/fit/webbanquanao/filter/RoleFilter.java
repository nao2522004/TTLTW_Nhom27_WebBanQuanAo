package vn.edu.hcmuaf.fit.webbanquanao.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUser;

import java.io.IOException;

@WebFilter("/*") // Áp dụng cho toàn bộ request
public class RoleFilter implements Filter {

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

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        // Kiểm tra nếu chưa đăng nhập
        if (userObj == null && isAdminPage(requestURI, contextPath)) {
            showAlertAndRedirect(httpResponse, "Bạn chưa đăng nhập!", contextPath + "/login.jsp");
            return;
        }

        // Kiểm tra quyền nếu đã đăng nhập
        if (userObj instanceof AUser) {
            AUser authUser = (AUser) userObj;
            String role = authUser.getRoleName();

            if (isAdminPage(requestURI, contextPath) && !"ADMIN".equals(role)) {
                showAlertAndRedirect(httpResponse, "Bạn không có quyền truy cập!", contextPath + "/homePage");
                return;
            }
        }

        // Nếu đủ quyền thì tiếp tục
        chain.doFilter(request, response);
    }

    // Kiểm tra truy cập admin.jsp
    private boolean isAdminPage(String requestURI, String contextPath) {
        return requestURI.equals(contextPath + "/admin.jsp");
    }

    // Hiển thị thông báo và chuyển hướng
    private void showAlertAndRedirect(HttpServletResponse response, String message, String redirectURL) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<script>alert('" + message + "'); window.location.href='" + redirectURL + "';</script>");
    }

    @Override
    public void destroy() {
    }
}
