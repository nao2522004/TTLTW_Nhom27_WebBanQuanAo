package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.dao.TokenForgotDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.model.TokenForgotPassword;
import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.user.util.EmailUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet("/sendChangePasswordEmail")
public class SendChangePasswordEmailServlet extends HttpServlet {
    private UserDao userDao = new UserDao();
    private TokenForgotDao tokenDao = new TokenForgotDao();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = req.getReader().lines().collect(Collectors.joining());
        JsonObject jsonObj = JsonParser.parseString(json).getAsJsonObject();
        String userName = jsonObj.get("userName").getAsString();

        User user = userDao.getUserByUserName(userName);
        JsonObject result = new JsonObject();

        if (user != null && user.getEmail() != null) {
            String token = UUID.randomUUID().toString();
            LocalDateTime expiresAt = LocalDateTime.now().plusHours(1); // token hết hạn sau 1 giờ

            // Lưu token và thời gian hết hạn vào DB
            TokenForgotPassword tokenData = new TokenForgotPassword(user.getId(), token, expiresAt);
            tokenDao.insertTokenForgot(tokenData);

            String link = "http://localhost:8080/WebBanQuanAo/change-password?token=" + token;

            String subject = "Xác nhận đổi mật khẩu";
            String content = "Bạn vừa yêu cầu đổi mật khẩu. Vui lòng bấm link sau để đổi mật khẩu: " + link;

            try {
                EmailUtil.sendEmail(user.getEmail(), subject, content);
                result.addProperty("success", true);
            } catch (Exception e) {
                result.addProperty("success", false);
            }
        } else {
            result.addProperty("success", false);
        }

        resp.setContentType("application/json");
        resp.getWriter().write(result.toString());
    }
}
