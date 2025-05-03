package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;
import vn.edu.hcmuaf.fit.webbanquanao.filter.AuthorizationFilter;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);  // Logger của SLF4J

    private UserLogsService userLogsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userLogsService = new UserLogsService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("auth");
            // Ghi log hành động đăng xuất
            if (user != null) {

                logger.info("Đăng xuất thành công cho người dùng: {}", user.getUserName());

                userLogsService.logLogoutSuccess(user.getUserName(), request.getRemoteAddr(), user.getRoles());
            }

            session.invalidate();
        }

        response.sendRedirect("login.jsp");
    }
}
