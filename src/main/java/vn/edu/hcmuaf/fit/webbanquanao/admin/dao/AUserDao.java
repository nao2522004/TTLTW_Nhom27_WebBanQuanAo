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
//    public boolean update(Object obj, String userName) {
//        return JDBIConnector.get().withHandle(handle -> {
//            AUser user = (AUser) obj;
//            listUser.replace(userName, user);
//            String sql = "UPDATE users SET id = ?, userName = ?, firstName = ?, lastName = ?, email = ?, avatar = ?, address = ?, phone = ?, status = ?, createdAt = ?, roleId = ? WHERE userName = ?";
//            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
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
////                ps.setInt(11, user.getRoleId());
//                ps.setString(12, userName);
//                return ps.executeUpdate() > 0;
//            } catch (Exception e) {
//                System.out.println("Loi khi update user: " + e.getMessage());
//            }
//            return false;
//        });
//    }
//
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
