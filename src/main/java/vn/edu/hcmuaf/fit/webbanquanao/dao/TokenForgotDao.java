package vn.edu.hcmuaf.fit.webbanquanao.dao;

import vn.edu.hcmuaf.fit.webbanquanao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.model.TokenForgotPassword;

import java.sql.PreparedStatement;
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
        String sql = "INSERT INTO [resetpasswordtokens]" +
                "    (token" +
                "    ,expiresAt" +
                "    ,isUser" +
                "    ,userId" +
                " VALUES(?,?,?,?))";
        return conn.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, tokenForgotPassword.getToken());
                ps.setTimestamp(2, Timestamp.valueOf(tokenForgotPassword.getExpiresAt()));
                ps.setBoolean(3, tokenForgotPassword.isUser());
                ps.setLong(4, tokenForgotPassword.getUserId());
                return ps.executeUpdate() > 0;

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        });
    }
}
