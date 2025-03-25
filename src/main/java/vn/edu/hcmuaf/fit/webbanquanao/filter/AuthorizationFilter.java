package vn.edu.hcmuaf.fit.webbanquanao.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUser;

import java.io.IOException;

@WebFilter(filterName = "AuthorizationFilter")
public class AuthorizationFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession();
        AUser user = (AUser) session.getAttribute("auth");

        if (user == null) {
            httpResponse.sendRedirect("login.jsp");
            return;
        }

        // Xác định quyền cần kiểm tra theo phương thức HTTP
        String method = httpRequest.getMethod();
        String requiredPermission = switch (method) {
            case "GET" -> "READ";
            case "POST" -> "RUN";
            case "PUT", "DELETE" -> "WRITE";
            default -> "";
        };

        // Kiểm tra quyền (nếu cần)
        if (!requiredPermission.isEmpty() && !user.hasPermission(requiredPermission)) {
            request.setAttribute("errorMessage", "Bạn không có quyền thực hiện thao tác này.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
