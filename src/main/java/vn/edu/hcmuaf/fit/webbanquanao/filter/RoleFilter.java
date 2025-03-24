//package vn.edu.hcmuaf.fit.webbanquanao.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUser;
//
//import java.io.IOException;
//import java.util.List;
//
//@WebFilter("/*")
//public class RoleFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        HttpSession session = httpRequest.getSession();
//        Object userObj = session.getAttribute("auth");
//
//        if (userObj instanceof AUser) {
//            AUser authUser = (AUser) userObj;
//
//            // Kiểm tra quyền theo URL
//            List<String> roles = authUser.getRoles();
//            String requestURI = httpRequest.getRequestURI();
//
//            if (hasAccess(requestURI, roles)) {
//                chain.doFilter(request, response);
//                return;
//            }
//        }
//
//        // Chuyển hướng nếu không có quyền
//        httpResponse.sendRedirect(httpRequest.getContextPath() + "/404NotFound.jsp");
//    }
//
//    private boolean hasAccess(String requestURI, List<String> roles) {
//        if (roles == null) return false;
//
//        if (requestURI.contains("/admin") && roles.contains("ADMIN")) {
//            return true;
//        }
////        if (requestURI.contains("/user") && roles.contains("USER")) {
////            return true;
////        }
////        if (requestURI.contains("/viewer") && roles.contains("VIEWER")) {
////            return true;
////        }
//
//        return false;
//    }
//
//    @Override
//    public void destroy() {
//    }
//}
