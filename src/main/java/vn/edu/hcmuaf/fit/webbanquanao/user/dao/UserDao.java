package vn.edu.hcmuaf.fit.webbanquanao.user.dao;

import org.mindrot.jbcrypt.BCrypt;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.model.TokenForgotPassword;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;


import java.sql.*;
import java.util.*;

public class UserDao {

    JDBIConnector dbConnect;

    public Map<String, User> listUser;

    public UserDao() {
        dbConnect = new JDBIConnector();
        listUser = getAllUser();
    }

    public Map<String, User> getAllUser() {
        Map<String, User> users = new LinkedHashMap<>();
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

                    User user = new User();
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
    public String getRoleNameById(String roleName) {
        String sql = "SELECT roleName FROM roles WHERE roleName = ?";

        return dbConnect.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, roleName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("roleName");
                    }
                }
            } catch (SQLException e) {
                System.err.println("❌ Lỗi khi lấy roleName: " + e.getMessage());
            }
            System.out.println("⚠ Không tìm thấy role: " + roleName);
            return null; // Trả về null nếu không tìm thấy
        });
    }


    public boolean registerUser(User user) {
        String userSql = "INSERT INTO users (userName, avatar, password, firstName, lastName, email, phone, address, status, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String checkRoleSql = "SELECT COUNT(*) FROM user_roles WHERE userId = ? AND roleId = ?";
        String insertRoleSql = "INSERT INTO user_roles (userId, roleId) VALUES (?, ?)";

        String hashedPassword = BCrypt.hashpw(user.getPassWord(), BCrypt.gensalt());

        return dbConnect.get().withHandle(handle -> {
            try (Connection conn = handle.getConnection()) {
                conn.setAutoCommit(false);

                try (PreparedStatement userPs = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                    userPs.setString(1, user.getUserName());
                    userPs.setString(2, user.getAvatar());
                    userPs.setString(3, hashedPassword);
                    userPs.setString(4, user.getFirstName());
                    userPs.setString(5, user.getLastName());
                    userPs.setString(6, user.getEmail());

                    if (user.getPhone() != null) {
                        userPs.setInt(7, user.getPhone());
                    } else {
                        userPs.setNull(7, java.sql.Types.INTEGER);
                    }

                    userPs.setString(8, user.getAddress());
                    int status = (user.getStatus() != null) ? user.getStatus() : 0; // Mặc định trạng thái là 0 (Chưa kích hoạt)
                    userPs.setInt(9, status);

                    Timestamp createdAt = (user.getCreatedAt() != null) ?
                            Timestamp.valueOf(user.getCreatedAt()) :
                            new Timestamp(System.currentTimeMillis());
                    userPs.setTimestamp(10, createdAt);

                    int rowsAffected = userPs.executeUpdate();
                    if (rowsAffected == 0) throw new SQLException("Tạo người dùng thất bại!");

                    try (ResultSet generatedKeys = userPs.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int userId = generatedKeys.getInt(1);

                            // 🔹 Lấy roleId của "USER"
                            int roleId = getRoleId("USER");
                            if (roleId == -1) {
                                throw new SQLException("Không tìm thấy role 'USER'!");
                            }

                            // 🔹 Kiểm tra trước khi thêm vào user_roles
                            try (PreparedStatement checkRolePs = conn.prepareStatement(checkRoleSql)) {
                                checkRolePs.setInt(1, userId);
                                checkRolePs.setInt(2, roleId);
                                try (ResultSet rs = checkRolePs.executeQuery()) {
                                    if (rs.next() && rs.getInt(1) > 0) {
                                        System.out.println("User đã có vai trò này, không cần thêm!");
                                    } else {
                                        try (PreparedStatement rolePs = conn.prepareStatement(insertRoleSql)) {
                                            rolePs.setInt(1, userId);
                                            rolePs.setInt(2, roleId);
                                            rolePs.executeUpdate();
                                        }
                                    }
                                }
                            }
                        } else {
                            throw new SQLException("Tạo người dùng thất bại, không có ID!");
                        }
                    }

                    conn.commit();
                    return true;
                } catch (SQLException e) {
                    conn.rollback();
                    System.err.println("Lỗi khi đăng ký người dùng: " + e.getMessage());
                    e.printStackTrace();
                    return false;
                }
            } catch (SQLException e) {
                System.err.println("Lỗi kết nối database: " + e.getMessage());
                return false;
            }
        });
    }


    public int getRoleId(String roleName) {
        String sql = "SELECT id FROM roles WHERE roleName = ?";

        return dbConnect.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, roleName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id");
                    }
                }
            } catch (SQLException e) {
                System.err.println("❌ Lỗi khi lấy roleId: " + e.getMessage());
            }
            return -1; // Trả về -1 nếu không tìm thấy
        });
    }



    // Kiểm tra xem email đã tồn tại chưa
    public boolean isEmailExist(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        return dbConnect.get().withHandle(handle -> {
            try (Connection conn = handle.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        });
    }


    public boolean updatePassword(String email, String hashedPassword) {
        return JDBIConnector.get().withHandle(handle -> {
            String sql = "UPDATE users SET password = ? WHERE email = ?";
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, hashedPassword);
                ps.setString(2, email);
                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        });
    }


    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        return dbConnect.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setUserName(rs.getString("userName"));
                        user.setPassWord(rs.getString("passWord"));
                        user.setFirstName(rs.getString("firstName"));
                        user.setLastName(rs.getString("lastName"));
                        user.setEmail(rs.getString("email"));
                        user.setAvatar(rs.getString("avatar"));
                        user.setPhone(rs.getInt("phone"));
                        user.setAddress(rs.getString("address"));
                        user.setStatus(rs.getInt("status"));
//                        user.setRoleId(rs.getInt("roleId"));
                        user.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
                        return user;
                    }
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy thông tin người dùng: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        });
    }


    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        return dbConnect.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id); // Sử dụng ID để tìm người dùng
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setUserName(rs.getString("userName"));
                        user.setPassWord(rs.getString("passWord"));
                        user.setFirstName(rs.getString("firstName")); // Thêm thông tin nếu cần
                        user.setLastName(rs.getString("lastName"));
                        user.setEmail(rs.getString("email"));
                        return user;
                    }

                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy thông tin người dùng: " + e.getMessage());
                e.printStackTrace(); // In chi tiết lỗi để debug
            }
            return null;
        });
    }


    // Cập nhật trạng thái của token
    public void updateStatus(TokenForgotPassword token) {
        System.out.println("token=" + token);
        String sql = "UPDATE users SET isUser = ? WHERE token = ?";
        dbConnect.get().withHandle(handle -> {
            try (Connection conn = handle.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setBoolean(1, token.isIsUsed());
                ps.setString(2, token.getToken());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e);
            }
            return null;
        });
    }

    // Đổi mật khẩu trong trang cá nhân
    public boolean changePassword(String userName, String hashedPassword) {
        return JDBIConnector.get().withHandle(handle -> {
            String sql = "UPDATE users SET password = ? WHERE username = ?";
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, hashedPassword); // Lưu mật khẩu đã mã hóa
                ps.setString(2, userName);
                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        });
    }


    public String getPassWordByUserName(String userName) {
        String sql = "SELECT password FROM users WHERE userName = ?";

        return JDBIConnector.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, userName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("passWord");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean checkPassword(String inputPassword, String hashedPassword) {
        return BCrypt.checkpw(inputPassword, hashedPassword);
    }

    public boolean addUser(User user) {
        return JDBIConnector.get().withHandle(handle -> {
            String sql = "INSERT INTO users (userName, email, password, firstName, lastName, phone, roleId, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getEmail());
                ps.setString(3, hashPassword(user.getPassWord()));
                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        });
    }


    public ArrayList<String> getRoleNameByUserName(String userName) {
        String sql = "SELECT r.roleName FROM users u JOIN user_roles ur ON u.id = ur.userId JOIN roles r ON ur.roleId = r.id WHERE u.userName = ?";

        return JDBIConnector.get().withHandle(handle -> {
            ArrayList<String> roles = new ArrayList<>();
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, userName);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                       roles.add(rs.getString("roleName"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return roles;
        });
    }

    public ArrayList<String> getPemissionNameByUserName(String userName) {
        String sql = "SELECT DISTINCT p.permissionName FROM users u JOIN user_roles ur ON u.id = ur.userId JOIN roles r ON ur.roleId = r.id JOIN role_permissions rp ON r.id = rp.roleId JOIN permissions p ON rp.permissionId = p.id WHERE u.userName = ?";

        return JDBIConnector.get().withHandle(handle -> {
            ArrayList<String> permissionNames = new ArrayList<>();
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, userName);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        permissionNames.add(rs.getString("permissionName"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return permissionNames;
        });
    }
}