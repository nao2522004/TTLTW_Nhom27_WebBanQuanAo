package vn.edu.hcmuaf.fit.webbanquanao.user.dao;

import org.mindrot.jbcrypt.BCrypt;
import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.model.TokenForgotPassword;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserDao {

    JDBIConnector dbConnect;

    public Map<String, User> listUser;

    public UserDao() {
        dbConnect = new JDBIConnector();
        listUser = getAllUser();
    }

    public Map<String, User> getAllUser() {
        Map<String, User> users = new LinkedHashMap<>();
        String sql = "SELECT * FROM users ORDER BY id DESC";

        return JDBIConnector.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUserName(rs.getString("userName"));
                    user.setPassWord(rs.getString("passWord"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setEmail(rs.getString("email"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setAddress(rs.getString("address"));
                    user.setPhone(rs.getInt("phone"));
                    user.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
                    user.setStatus(rs.getInt("status"));
//                    user.setRoleId(rs.getInt("roleId"));
                    users.put(user.getUserName(), user);
                }
            } catch (Exception e) {
                System.out.println("Loi khi lay danh sach user: " + e.getMessage());
            }
            return users;
        });
    }

    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (avatar, password, firstName, lastName, email, phone, address, roleId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String hashedPassword = BCrypt.hashpw(user.getPassWord(), BCrypt.gensalt());

        return dbConnect.get().withHandle(handle -> {
            try (Connection conn = handle.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, user.getAvatar());
                ps.setString(2, hashedPassword);
                ps.setString(3, user.getFirstName());
                ps.setString(4, user.getLastName());
                ps.setString(5, user.getEmail());

                // Kiểm tra nếu phone là null thì setNull, ngược lại thì setInt
                if (user.getPhone() != null) {
                    ps.setInt(6, user.getPhone());
                } else {
                    ps.setNull(6, java.sql.Types.INTEGER);
                }

                ps.setString(7, user.getAddress());
//                ps.setInt(8, user.getRoleId());

                int rowsAffected = ps.executeUpdate();
                System.out.println("Rows affected: " + rowsAffected);
                return rowsAffected > 0;

            } catch (SQLException e) {
                System.err.println("Lỗi khi đăng ký người dùng: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
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

}