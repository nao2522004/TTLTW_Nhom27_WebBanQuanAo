
package vn.edu.hcmuaf.fit.webbanquanao.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.User;

import java.io.IOException;

@WebFilter(filterName = "AdminFilter", urlPatterns = "/admin/*")
public class AdminFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override

    public void doFilter(ServletRequest re, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) re;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(true);
        User u = (User) session.getAttribute("auth");
        if(u==null || u.getRoleName().equalsIgnoreCase("USER")) {
            response.sendRedirect("../login.jsp");
            return;
        }

        // In thông tin user ra console
        System.out.println("User logged in: " + u.getUsername() + " | Role ID: " + u.getRole());

        chain.doFilter(request, response);
    }
}