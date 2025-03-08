package vn.edu.hcmuaf.fit.webbanquanao.user.auth.dao;

import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.model.TokenForgotPassword;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TokenForgotDao {
    JDBIConnector conn;

    public TokenForgotDao() {
        this.conn = new JDBIConnector();
    }

    public String getFormatData(LocalDateTime myDataObj) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = myDataObj.format(formatter);
        return format;
    }

    public boolean insertTokenForgot(TokenForgotPassword tokenForgotPassword) {
        String sql = "INSERT INTO resetpasswordtokens (token, expiredAt, userId) VALUES (?, ?, ?)";
        return conn.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, tokenForgotPassword.getToken());
                ps.setTimestamp(2, Timestamp.valueOf(getFormatData(tokenForgotPassword.getExpiresAt()))); // expiredAt should be set properly
                ps.setLong(3, tokenForgotPassword.getUserId()); // Make sure userId is correct
                return ps.executeUpdate() > 0; // Return true if token was inserted successfully
            } catch (SQLException e) {
                e.printStackTrace();
                return false; // If insertion fails, return false
            }
        });
    }

    public TokenForgotPassword getTokenForgot(String token) {
        String sql = "SELECT * FROM resetpasswordtokens WHERE token = ?";

        return conn.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, token); // Gán giá trị token vào câu lệnh SQL

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Tạo đối tượng TokenForgotPassword và gán các giá trị từ ResultSet vào
                        TokenForgotPassword tokenForgotPassword = new TokenForgotPassword();
                        tokenForgotPassword.setId(rs.getInt("id"));
                        tokenForgotPassword.setUserId(rs.getInt("userId"));
                        tokenForgotPassword.setToken(rs.getString("token"));
                        tokenForgotPassword.setExpiredAt(rs.getTimestamp("expiredAt").toLocalDateTime());
                        return tokenForgotPassword;
                    }
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy token từ cơ sở dữ liệu: " + e.getMessage());
                e.printStackTrace();
            }
            return null; // Trả về null nếu không tìm thấy token
        });
    }
}
