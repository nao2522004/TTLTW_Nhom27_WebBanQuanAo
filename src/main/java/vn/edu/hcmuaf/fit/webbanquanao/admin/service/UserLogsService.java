package vn.edu.hcmuaf.fit.webbanquanao.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.hcmuaf.fit.webbanquanao.admin.dao.UserLogsDao;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.UserLogs;

import java.util.Collections;
import java.util.List;

public class UserLogsService {
    private static final Logger logger = LoggerFactory.getLogger(UserLogsService.class);
    private static final UserLogsService instance = new UserLogsService(); // Singleton instance

    // Trả về instance duy nhất
    public static UserLogsService getInstance() {
        return instance;
    }

    private final UserLogsDao userLogsDao;

    public UserLogsService() {
        this.userLogsDao = new UserLogsDao();
    }

    // Điểm vào chung để ghi log với các thông tin cơ bản
    public boolean logAction(String level,
                              String username,
                              List<String> roles,
                              String action,
                              String ipAddress
    ) {
        if (!isValidLogLevel(level)) {
            System.err.println("Cấp độ log không hợp lệ: " + level);
            return false;
        }
        UserLogs log = new UserLogs();
        log.setUsername(username);
        log.setLevel(level);
        log.setAction(action);
        log.setIpAddress(ipAddress);
        log.setRoles(roles);
        return userLogsDao.logUserAction(log);
    }


    // Kiểm tra cấp độ log hợp lệ
    public boolean isValidLogLevel(String level) {
        return level.equals("DEBUG")
                || level.equals("INFO")
                || level.equals("WARN")
                || level.equals("ERROR")
                || level.equals("FATAL");
    }

    // 1. Ghi log khi user chưa đăng nhập cố gắng truy cập tài nguyên bảo vệ
    public boolean logAnonymousAccessAttempt(String path, String ipAddress) {
        return logAction(
                "WARN",
                "ANONYMOUS",
                Collections.emptyList(),
                "User ẩn danh cố truy cập đường dẫn: " + path,
                ipAddress
        );
    }

    // 2. Ghi log thông tin người dùng và roles (chỉ một lần sau khi load session)
    public boolean logLoadedUserInfo(String username, List<String> roles, String ipAddress) {
        return logAction(
                "INFO",
                username,
                roles,
                "Đã load thông tin user với roles: " + roles,
                ipAddress
        );
    }

    // 3. Ghi log khi chặn login do tài khoản chưa kích hoạt hoặc thiếu auth
    public boolean logBlockedLogin(String username, String ipAddress) {
        return logAction(
                "WARN",
                username,
                Collections.emptyList(),
                "Chặn đăng nhập do tài khoản chưa kích hoạt hoặc thiếu xác thực",
                ipAddress
        );
    }

    // 4. Ghi log khi truy cập không đủ quyền
    public boolean logUnauthorizedAccess(String username, String path, String resource, Integer permission, String ipAddress, List<String> roles) {
        return logAction(
                "WARN",
                username,
                roles,
                String.format("Không có quyền truy cập : đường dẫn=%s, resource=%s, quyền=%s", path, resource, permission),
                ipAddress
        );
    }

    // 5. Ghi log khi truy cập được phép
    public boolean logAccessGranted(String username, String path, String resource, Integer permission, String ipAddress, List<String> roles) {
        return logAction(
                "INFO",
                username,
                roles,
                String.format("Truy cập thành công: đường dẫn=%s, resource=%s, quyền=%s", path, resource, permission),
                ipAddress
        );
    }

    // 6. Ghi log đăng nhập thành công
    public boolean logLoginSuccess(String username, String ipAddress, List<String> roles) {
        return logAction(
                "INFO",
                username,
                roles,
                "Đăng nhập thành công",
                ipAddress
        );
    }

    // 6.1. Ghi log đăng nhập thành công
    public boolean logLogoutSuccess(String username, String ipAddress, List<String> roles) {
        return logAction(
                "INFO",
                username,
                roles,
                "Đăng xuất thành công",
                ipAddress
        );
    }

    // 7. Ghi log đăng nhập thất bại
    public boolean logLoginFailure(String username, String ipAddress) {
        return logAction(
                "ERROR",
                username,
                Collections.emptyList(),
                "Đăng nhập thất bại",
                ipAddress
        );
    }

    // 8. Ghi log tạo mới đối tượng (add)
    public boolean logCreateEntity(String username, String entityName, String entityId, String ipAddress, List<String> roles) {
        return logAction(
                "INFO",
                username,
                roles,
                String.format("Tạo mới %s thành công: %s", entityName, entityId),
                ipAddress
        );
    }

    // 9. Ghi log cập nhật đối tượng (update)
    public boolean logUpdateEntity(String username, String entityName, String entityId, String ipAddress, List<String> roles) {
        return logAction(
                "INFO",
                username,
                roles,
                String.format("Cập nhật %s: %s", entityName, entityId),
                ipAddress
        );
    }

    // 10. Ghi log xóa đối tượng (delete)
    public boolean logDeleteEntity(String username, String entityName, String entityId, String ipAddress, List<String> roles) {
        return logAction(
                "WARN",
                username,
                roles,
                String.format("Xóa %s: %s", entityName, entityId),
                ipAddress
        );
    }

    // 11. Ghi log phân vai trò cho user
    public boolean logAssignRoles(String username, String targetUser, List<String> newRoles, String ipAddress, List<String> roles) {
        return logAction(
                "INFO",
                username,
                roles,
                String.format("Phân roles %s cho user: %s", newRoles, targetUser),
                ipAddress
        );
    }

    // 12. Ghi log hành động tùy chỉnh
    public boolean logCustom(String username, String level, String action, String ipAddress, List<String> roles) {
        return logAction(level, username, roles, action, ipAddress);
    }

    // 13. Ghi log khi truy cập bị từ chối do thiếu quyền
    public boolean logAccessDenied(String username, String path, String resource, String ipAddress, List<String> roles) {
        return logAction(
                "ERROR",
                username,
                roles,
                String.format("Truy cập bị từ chối: đường dẫn=%s, resource=%s", path, resource),
                ipAddress
        );
    }
}