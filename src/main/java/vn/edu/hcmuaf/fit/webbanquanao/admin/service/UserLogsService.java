package vn.edu.hcmuaf.fit.webbanquanao.admin.service;

import vn.edu.hcmuaf.fit.webbanquanao.admin.dao.UserLogsDao;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.UserLogs;

import java.util.Collections;
import java.util.List;

public class UserLogsService {
    private final UserLogsDao userLogsDao;

    // Khởi tạo DAO để ghi log
    public UserLogsService() {
        this.userLogsDao = new UserLogsDao();
    }

    // Điểm vào chung để ghi log với các thông tin cơ bản
    private boolean logAction(String username,
                              String level,
                              String action,
                              String ipAddress,
                              List<String> roles) {
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
    private boolean isValidLogLevel(String level) {
        return level.equals("DEBUG")
                || level.equals("INFO")
                || level.equals("WARN")
                || level.equals("ERROR")
                || level.equals("FATAL");
    }

    // 1. Ghi log khi user chưa đăng nhập cố gắng truy cập tài nguyên bảo vệ
    public boolean logAnonymousAccessAttempt(String path, String ipAddress) {
        return logAction(
                "ANONYMOUS",
                "WARN",
                "User ẩn danh cố truy cập đường dẫn: " + path,
                ipAddress,
                Collections.emptyList()
        );
    }

    // 2. Ghi log thông tin người dùng và roles (chỉ một lần sau khi load session)
    public boolean logLoadedUserInfo(String username, List<String> roles, String ipAddress) {
        return logAction(
                username,
                "INFO",
                "Đã load thông tin user với roles: " + roles,
                ipAddress,
                roles
        );
    }

    // 3. Ghi log khi chặn login do tài khoản chưa kích hoạt hoặc thiếu auth
    public boolean logBlockedLogin(String username, String ipAddress) {
        return logAction(
                username,
                "WARN",
                "Chặn đăng nhập do tài khoản chưa kích hoạt hoặc thiếu xác thực",
                ipAddress,
                Collections.emptyList()
        );
    }

    // 4. Ghi log khi truy cập không đủ quyền
    public boolean logUnauthorizedAccess(String username,
                                         String path,
                                         String resource,
                                         Integer permission,
                                         String ipAddress,
                                         List<String> roles) {
        return logAction(
                username,
                "WARN",
                String.format("Cố truy cập không đủ quyền: đường dẫn=%s, resource=%s, quyền=%s", path, resource, permission),
                ipAddress,
                roles
        );
    }

    // 5. Ghi log khi truy cập được phép
    public boolean logAccessGranted(String username,
                                    String path,
                                    String resource,
                                    Integer permission,
                                    String ipAddress,
                                    List<String> roles) {
        return logAction(
                username,
                "INFO",
                String.format("Truy cập thành công: đường dẫn=%s, resource=%s, quyền=%s", path, resource, permission),
                ipAddress,
                roles
        );
    }

    // 6. Ghi log đăng nhập thành công
    public boolean logLoginSuccess(String username, String ipAddress, List<String> roles) {
        return logAction(
                username,
                "INFO",
                "Đăng nhập thành công",
                ipAddress,
                roles
        );
    }

    // 7. Ghi log đăng nhập thất bại
    public boolean logLoginFailure(String username, String ipAddress) {
        return logAction(
                username,
                "ERROR",
                "Đăng nhập thất bại",
                ipAddress,
                Collections.emptyList()
        );
    }

    // 8. Ghi log tạo mới đối tượng (add)
    public boolean logCreateEntity(String username,
                                   String entityName,
                                   String entityId,
                                   String ipAddress,
                                   List<String> roles) {
        return logAction(
                username,
                "INFO",
                String.format("Tạo mới %s thành công: %s", entityName, entityId),
                ipAddress,
                roles
        );
    }

    // 9. Ghi log cập nhật đối tượng (update)
    public boolean logUpdateEntity(String username,
                                   String entityName,
                                   String entityId,
                                   String ipAddress,
                                   List<String> roles) {
        return logAction(
                username,
                "INFO",
                String.format("Cập nhật %s: %s", entityName, entityId),
                ipAddress,
                roles
        );
    }

    // 10. Ghi log xóa đối tượng (delete)
    public boolean logDeleteEntity(String username,
                                   String entityName,
                                   String entityId,
                                   String ipAddress,
                                   List<String> roles) {
        return logAction(
                username,
                "WARN",
                String.format("Xóa %s: %s", entityName, entityId),
                ipAddress,
                roles
        );
    }

    // 11. Ghi log phân vai trò cho user
    public boolean logAssignRoles(String username,
                                  String targetUser,
                                  List<String> newRoles,
                                  String ipAddress,
                                  List<String> roles) {
        return logAction(
                username,
                "INFO",
                String.format("Phân roles %s cho user: %s", newRoles, targetUser),
                ipAddress,
                roles
        );
    }

    // 12. Ghi log hành động tùy chỉnh
    public boolean logCustom(String username,
                             String level,
                             String action,
                             String ipAddress,
                             List<String> roles) {
        return logAction(username, level, action, ipAddress, roles);
    }
}