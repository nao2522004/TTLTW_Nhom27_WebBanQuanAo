package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.service.GoogleUserInfo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@WebServlet("/google-callback")
public class GoogleCallbackServlet extends HttpServlet {
    private static final String CLIENT_ID = "YOUR_CLIENT_ID";
    private static final String CLIENT_SECRET = "YOUR_CLIENT_SECRET";
    private static final String REDIRECT_URI = "http://localhost:8080/WebBanQuanAo/google-callback";
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        if (code == null || code.isEmpty()) {
            resp.sendRedirect("login.jsp?error=Google authentication failed");
            return;
        }

        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                CLIENT_ID,
                CLIENT_SECRET,
                Arrays.asList("https://www.googleapis.com/auth/userinfo.email", "https://www.googleapis.com/auth/userinfo.profile")
        ).build().newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();

        String email = GoogleUserInfo.getEmail(tokenResponse.getAccessToken());
        String name = GoogleUserInfo.getFullName(tokenResponse.getAccessToken());

        HttpSession session = req.getSession();

        if (userDao.isEmailExist(email)) {
            session.setAttribute("auth", email);
        } else {
            String username = email.split("@")[0];
            userDao.createUser(username, name, email, null);
            session.setAttribute("auth", email);
        }

        resp.sendRedirect("homePage.jsp");
    }
}
