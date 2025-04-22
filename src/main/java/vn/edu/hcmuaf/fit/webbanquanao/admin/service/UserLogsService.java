package vn.edu.hcmuaf.fit.webbanquanao.admin.service;

import vn.edu.hcmuaf.fit.webbanquanao.admin.dao.UserLogsDao;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.UserLog;

public class UserLogsService {
    private UserLogsDao userLogsDao;


    // Ghi log hành động người dùng (có thể tùy chỉnh theo loại hành động)
    public boolean logAction(String username, String level, String action, String ipAddress) {
        // Kiểm tra cấp độ log có hợp lệ không
        if (isValidLogLevel(level)) {
            UserLog userLog = new UserLog(username, level, action, ipAddress);
            return userLogsDao.logUserAction(userLog);
        } else {
            // Nếu cấp độ log không hợp lệ, có thể ghi log báo lỗi hoặc làm gì đó khác
            System.out.println("Cấp độ log không hợp lệ: " + level);
            return false;
        }
    }

    // Kiểm tra cấp độ log hợp lệ
    private boolean isValidLogLevel(String level) {
        return level.equals("DEBUG") || level.equals("INFO") || level.equals("WARN") || level.equals("ERROR") || level.equals("FATAL");
    }

    // Ghi log đăng nhập thành công
    public boolean logSuccessfulLogin(String username, String ipAddress) {
        return logAction(username, "INFO", "User logged in successfully", ipAddress);
    }

    // Ghi log đăng nhập thất bại
    public boolean logFailedLogin(String username, String ipAddress) {
        return logAction(username, "ERROR", "Invalid login attempt", ipAddress);
    }

    // Ghi log cảnh báo (ví dụ, khi có sự cố nhẹ)
    public boolean logWarning(String username, String action, String ipAddress) {
        return logAction(username, "WARN", action, ipAddress);
    }

    // Ghi log các hành động khác (đơn giản nhưng hiệu quả)
    public boolean logCustomAction(String username, String level, String action, String ipAddress) {
        return logAction(username, level, action, ipAddress);
    }
}
