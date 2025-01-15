package vn.edu.hcmuaf.fit.webbanquanao.model;

import java.time.LocalDateTime;

public class TokenForgotPassword {
    private int id, userId;
    private boolean isUser;
    private String token;
    private LocalDateTime expiresAt;

    public TokenForgotPassword() {};

    public TokenForgotPassword(int id, int userId, boolean isUser, String token, LocalDateTime expiresAt) {
        this.id = id;
        this.userId = userId;
        this.isUser = isUser;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public TokenForgotPassword( int userId, boolean isUser, String token, LocalDateTime expiresAt) {
        this.userId = userId;
        this.isUser = isUser;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public String toString() {
        return "TokenForgotPassword{" +
                "id=" + id +
                ", userId=" + userId +
                ", isUser=" + isUser +
                ", token='" + token + '\'' +
                ", expiresAt=" + expiresAt +
                '}';
    }
}
