package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.model.FacebookUser;
import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

@WebServlet(name = "facebook-callback", value = "/facebook-callback")
public class FacebookCallbackServlet extends HttpServlet {
    private static final String CLIENT_ID = "your_client";
    private static final String CLIENT_SECRET = "your_client";
    private static final String REDIRECT_URI = "";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            response.sendRedirect("login.jsp?error=Facebook login failed");
            return;
        }

        // Lấy access token từ Facebook
        String tokenUrl = "https://graph.facebook.com/v18.0/oauth/access_token?"
                + "client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&client_secret=" + CLIENT_SECRET
                + "&code=" + code;

        String accessToken = getAccessToken(tokenUrl);

        // Lấy thông tin người dùng
        String userInfo = getUserInfo(accessToken);

        // Chuyển đổi JSON response sang Java Object
        FacebookUser fbUser = parseFacebookUser(userInfo);

        // Xử lý đăng nhập hoặc đăng ký tài khoản mới
        UserDao userDAO = new UserDao();
        User user = userDAO.getUserByEmail(fbUser.getEmail());

        if (user == null) {
            // Chưa có tài khoản, tạo mới
            String[] nameParts = fbUser.getName().split(" ");
            String firstName = nameParts.length > 0 ? nameParts[0] : "";
            String lastName = nameParts.length > 1 ? nameParts[1] : "";

            // Tạo username từ email (lấy phần trước @)
            String username = fbUser.getEmail().split("@")[0];

            // Tạo mật khẩu ngẫu nhiên và hash
            String randomPassword = userDAO.hashPassword(generateRandomPassword());

            // Tạo user mới
            user = new User(
                    username,
                    firstName,
                    lastName,
                    fbUser.getEmail(),
                    randomPassword
            );

            // Thiết lập các thông tin khác
            user.setStatus(1); // 1 = active
            user.setCreatedAt(LocalDateTime.now());
            user.setRoles(new ArrayList<>(List.of("USER"))); // Mặc định role USER

            // Thêm user vào database
            if (!userDAO.createUser(user)) {
                response.sendRedirect("login.jsp?error=Failed to create account");
                return;
            }

            // Lấy lại user từ DB để có đầy đủ thông tin
            user = userDAO.getUserByEmail(fbUser.getEmail());
        } else if (user.getStatus() == 0) {
            // Tài khoản bị vô hiệu hóa
            response.sendRedirect("login.jsp?error=Account is disabled");
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);


        response.sendRedirect("homePage");
    }

    private String getAccessToken(String tokenUrl) throws IOException {
        URL url = new URL(tokenUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject json = new JSONObject(response.toString());
        return json.getString("access_token");
    }

    private String getUserInfo(String accessToken) throws IOException {
        String url = "https://graph.facebook.com/me?fields=id,name,email&access_token=" + accessToken;
        return sendGetRequest(url);
    }

    private String sendGetRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private FacebookUser parseFacebookUser(String json) {
        JSONObject obj = new JSONObject(json);
        return new FacebookUser(obj.getString("id"), obj.getString("name"), obj.optString("email"));
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }
    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}