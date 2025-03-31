package vn.edu.hcmuaf.fit.webbanquanao.admin.dao;

import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUser;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.Permission;
import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class AUserDao {

    public Map<String, AUser> listUser;

    public AUserDao() {
        listUser = getAllUser();
    }

    public Map<String, AUser> getAllUser() {
        Map<String, AUser> users = new LinkedHashMap<>();
        String sql = "SELECT u.id, u.userName, u.passWord, u.firstName, u.lastName, u.email, " +
                "       u.avatar, u.address, u.phone, u.createdAt, u.status, " +
                "       GROUP_CONCAT(DISTINCT r.roleName ORDER BY r.roleName ASC) AS roleName, " +
                "       GROUP_CONCAT(DISTINCT p.permissionName ORDER BY p.permissionName ASC) AS permissionName " +
                "FROM users u " +
                "LEFT JOIN user_roles ur ON u.id = ur.userId " +
                "LEFT JOIN roles r ON ur.roleId = r.id " +
                "LEFT JOIN role_permissions rp ON r.id = rp.roleId " +
                "LEFT JOIN permissions p ON rp.permissionId = p.id " +
                "GROUP BY u.id " +
                "ORDER BY u.id DESC;";

        return JDBIConnector.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String userName = rs.getString("userName");

                    AUser user = new AUser();
                    user.setId(rs.getInt("id"));
                    user.setUserName(userName);
                    user.setPassWord(rs.getString("passWord"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setEmail(rs.getString("email"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setAddress(rs.getString("address"));
                    user.setPhone(rs.getInt("phone"));
                    user.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
                    user.setStatus(rs.getInt("status"));

                    // Kiểm tra null trước khi phân tách danh sách, dùng ArrayList thay vì List
                    user.setRoleName(rs.getString("roleName") != null ?
                            new ArrayList<>(Arrays.asList(rs.getString("roleName").split(","))) :
                            new ArrayList<>());

                    user.setPermissionName(rs.getString("permissionName") != null ?
                            new ArrayList<>(Arrays.asList(rs.getString("permissionName").split(","))) :
                            new ArrayList<>());

                    users.put(userName, user);
                }
            } catch (Exception e) {
                System.out.println("Lỗi khi lấy danh sách user: " + e.getMessage());
            }
            return users;
        });
    }


//    public boolean create(Object obj) {
//        AUser user = (AUser) obj;
//        listUser.put(user.getUserName(), user);
//
//        String sql = "INSERT INTO users (userName, password, firstName, lastName, email, avatar, address, phone, status, roleId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//        return JDBIConnector.get().withHandle(handle -> {
//            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//                ps.setString(1, user.getUserName());
//                ps.setString(2, user.getPassWord());
//                ps.setString(3, user.getFirstName());
//                ps.setString(4, user.getLastName());
//                ps.setString(5, user.getEmail());
//                ps.setString(6, user.getAvatar());
//                ps.setString(7, user.getAddress()); // ✅ Sửa lại
//                ps.setInt(8, user.getPhone());   // 🔥 FIX: Dùng setString() thay vì setInt()
//                ps.setInt(9, user.getStatus());
////                ps.setInt(10, user.getRoleId());
//
//                int affectedRows = ps.executeUpdate();
//                if (affectedRows > 0) {
//                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
//                        if (generatedKeys.next()) {
//                            int newId = generatedKeys.getInt(1);
//                            user.setId(newId); // Lưu ID mới vào đối tượng User
//                        }
//                    }
//                    return true;
//                }
//            } catch (Exception e) {
//                System.out.println("Lỗi khi tạo user: " + e.getMessage());
//            }
//            return false;
//        });
//    }
//
//
//

//public boolean update(Object obj, String userName) {
//    return JDBIConnector.get().withHandle(handle -> {
//        AUser user = (AUser) obj;
//        listUser.replace(userName, user);
//
//        Connection conn = handle.getConnection();
//        try {
//            conn.setAutoCommit(false); // Bắt đầu transaction
//
//            // 1️⃣ Cập nhật thông tin user trong bảng `users`
//            String updateUserSQL = "UPDATE users SET id = ?, userName = ?, firstName = ?, lastName = ?, email = ?, avatar = ?, address = ?, phone = ?, status = ?, createdAt = ? WHERE userName = ?";
//            try (PreparedStatement ps = conn.prepareStatement(updateUserSQL)) {
//                ps.setInt(1, user.getId());
//                ps.setString(2, user.getUserName());
//                ps.setString(3, user.getFirstName());
//                ps.setString(4, user.getLastName());
//                ps.setString(5, user.getEmail());
//                ps.setString(6, user.getAvatar());
//                ps.setString(7, user.getAddress());
//                ps.setInt(8, user.getPhone());
//                ps.setInt(9, user.getStatus());
//                ps.setDate(10, java.sql.Date.valueOf(user.getCreatedAt().toLocalDate()));
//                ps.setString(11, userName);
//                ps.executeUpdate();
//            }
//
//            // 2️⃣ Xóa vai trò cũ trong bảng `user_roles`
//            String deleteUserRolesSQL = "DELETE FROM user_roles WHERE userId = ?";
//            try (PreparedStatement ps = conn.prepareStatement(deleteUserRolesSQL)) {
//                ps.setInt(1, user.getId());
//                ps.executeUpdate();
//            }
//
//            // 3️⃣ Thêm vai trò mới vào `user_roles`
//            String insertUserRolesSQL = "INSERT INTO user_roles (userId, roleId) VALUES (?, ?)";
//            try (PreparedStatement ps = conn.prepareStatement(insertUserRolesSQL)) {
//                for (String role : user.getRoleName()) {
//                    int roleId = getRoleIdByName(role); // Chuyển roleName thành roleId
//                    if (roleId != -1) {
//                        ps.setInt(1, user.getId());
//                        ps.setInt(2, roleId);
//                        ps.addBatch();
//                    }
//                }
//                ps.executeBatch();
//            }
//
//            // 4️⃣ Xóa quyền cũ trong bảng `role_permissions`
//            String deletePermissionsSQL = "DELETE FROM role_permissions WHERE roleId IN (SELECT roleId FROM user_roles WHERE userId = ?)";
//            try (PreparedStatement ps = conn.prepareStatement(deletePermissionsSQL)) {
//                ps.setInt(1, user.getId());
//                ps.executeUpdate();
//            }
//
//            // 5️⃣ Thêm quyền mới vào `role_permissions`
//            String insertPermissionsSQL = "INSERT INTO role_permissions (roleId, permissionId) VALUES (?, ?)";
//            try (PreparedStatement ps = conn.prepareStatement(insertPermissionsSQL)) {
//                for (String permission : user.getPermissionName()) {
//                    int permissionId = getPermissionIdByName(permission); // Chuyển permissionName thành permissionId
//                    if (permissionId != -1) {
//                        for (String role : user.getRoleName()) {
//                            int roleId = getRoleIdByName(role);
//                            if (roleId != -1) {
//                                ps.setInt(1, roleId);
//                                ps.setInt(2, permissionId);
//                                ps.addBatch();
//                            }
//                        }
//                    }
//                }
//                ps.executeBatch();
//            }
//
//            conn.commit(); // Hoàn thành transaction
//            return true;
//        } catch (Exception e) {
//            try {
//                conn.rollback(); // Rollback nếu có lỗi
//            } catch (SQLException ex) {
//                System.out.println("Lỗi rollback: " + ex.getMessage());
//            }
//            System.out.println("Lỗi khi cập nhật user: " + e.getMessage());
//            return false;
//        } finally {
//            try {
//                conn.setAutoCommit(true); // Bật lại chế độ tự động commit
//            } catch (SQLException e) {
//                System.out.println("Lỗi khi bật lại AutoCommit: " + e.getMessage());
//            }
//        }
//    });
//}

//
//
//    public boolean delete(String userName, Integer status) {
//        return JDBIConnector.get().withHandle(handle -> {
//            String sql = "UPDATE users SET status = ? WHERE userName = ?";
//            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
//                ps.setInt(1, status);
//                ps.setString(2, userName);
//                return ps.executeUpdate() > 0;
//            } catch (Exception e) {
//                System.out.println("Lỗi khi xóa user: " + e.getMessage());
//            }
//            return false; // Trả về false nếu xảy ra lỗi
//        });
//    }
//
//
//    public boolean updateUser(AUser user) {
//        return JDBIConnector.get().withHandle(handle -> {
//            listUser.replace(user.getUserName(), user);
//            String sql = "UPDATE users SET userName = ?, firstName = ?, lastName = ?, email = ?, avatar = ?, address = ?, phone = ?, status = ?, createdAt = ? WHERE userName = ?";
//            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
//                ps.setString(1, user.getUserName());
//                ps.setString(2, user.getFirstName());
//                ps.setString(3, user.getLastName());
//                ps.setString(4, user.getEmail());
//                ps.setString(5, user.getAvatar());
//                ps.setString(6, user.getAddress());
//                ps.setInt(7, user.getPhone());
//                ps.setInt(8, user.getStatus());
//                ps.setDate(9, java.sql.Date.valueOf(user.getCreatedAt().toLocalDate()));
//                ps.setString(10, user.getUserName());
//                return ps.executeUpdate() > 0;
//            } catch (Exception e) {
//                System.out.println("Lỗi khi cập nhật user: " + e.getMessage());
//                return false;
//            }
//        });
//    }
}
