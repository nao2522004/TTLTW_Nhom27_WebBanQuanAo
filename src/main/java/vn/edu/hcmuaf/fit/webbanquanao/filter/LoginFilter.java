package vn.edu.hcmuaf.fit.webbanquanao.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/homePage.jsp", "/user.jsp"})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Debug: Kiểm tra session và auth
        if (session == null) {
            System.out.println("Session is null. Redirecting to login.");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        Object auth = session.getAttribute("auth");
        if (auth == null) {
            System.out.println("Auth attribute is missing. Redirecting to login.");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        System.out.println("User authenticated: " + auth.toString());
        chain.doFilter(request, response);
    }
}

