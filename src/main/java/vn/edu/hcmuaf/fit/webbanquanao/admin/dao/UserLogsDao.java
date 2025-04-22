package vn.edu.hcmuaf.fit.webbanquanao.admin.dao;

import vn.edu.hcmuaf.fit.webbanquanao.admin.model.UserLog;
import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserLogsDao {
    // Hàm ghi log vào bảng userLogs
    public boolean logUserAction(Object obj) {
        UserLog userLog = (UserLog) obj;
        String spl = "INSERT INTO userLogs (username, level, action, ipAddress) VALUES (?, ?, ?, ?)";
        return JDBIConnector.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(spl)) {
                ps.setString(1, userLog.getUsername());
                ps.setString(2, userLog.getLevel());
                ps.setString(3, userLog.getAction());
                ps.setString(4, userLog.getIpAddress());
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        });
    }
}
