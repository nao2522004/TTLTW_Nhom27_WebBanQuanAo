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
                    user.setRoleId(rs.getInt("roleId"));
                    users.put(user.getUserName(), user);
                }
            } catch (Exception e) {
                System.out.println("Loi khi lay danh sach user: " + e.getMessage());
            }
            return users;
        });
    }

    // Đăng ký người dùng mới (mã hóa mật khẩu)
    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (avatar, password, fullName, email, phone, address, notificationCheck, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String hashedPassword = BCrypt.hashpw(user.getPassWord(), BCrypt.gensalt());

        return dbConnect.get().withHandle(handle -> {
            try (Connection conn = handle.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, user.getAvatar());
                ps.setString(2, hashedPassword);
                ps.setString(3, user.getFirstName());
                ps.setString(3, user.getLastName());
                ps.setString(4, user.getEmail());
                ps.setInt(5, user.getPhone());
                ps.setString(6, user.getAddress());
                ps.setInt(7, user.getRoleId());

                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    // Kiểm tra xem email đã tồn tại chưa
    public boolean isEmailExist(String email) {
        String sql = "SELECT * FROM users WHERE gmail = ?";

        return dbConnect.get().withHandle(handle -> {
            try (Connection conn = handle.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                // Gắn giá trị cho tham số email
                ps.setString(1, email);

                // Thực thi câu lệnh SQL và kiểm tra xem có dòng nào trả về
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next(); // Nếu có ít nhất một kết quả, tức là email đã tồn tại
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false; // Trả về false nếu có lỗi
            }
        });
    }

    //  Hàm băm mật khẩu bằng BCrypt trước khi lưu
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    // Hàm kiểm tra mật khẩu đã băm
    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    // Cập nhật mật khẩu với BCrypt
    public void updatePassword(String email, String newPassword) {
        String hashedPassword = hashPassword(newPassword);
        String sql = "UPDATE users SET password = ? WHERE email = ?";

        dbConnect.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, hashedPassword);
                ps.setString(2, email);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Lỗi cập nhật mật khẩu: " + e.getMessage());
            }
            return null;
        });
    }

    // Lấy người dùng theo email
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        return dbConnect.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapUser(rs);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Lỗi lấy người dùng theo email: " + e.getMessage());
            }
            return null;
        });
    }

    // Lấy người dùng theo ID
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        return dbConnect.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapUser(rs);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Lỗi lấy người dùng theo ID: " + e.getMessage());
            }
            return null;
        });
    }

    // Hàm chung để map dữ liệu từ ResultSet vào User
    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUserName(rs.getString("userName"));
        user.setPassWord(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setFirstName(rs.getString("firstName"));
        user.setLastName(rs.getString("lastName"));
        return user;
    }

    // Cập nhật trạng thái của token
    public void updateTokenStatus(String token) {
        String sql = "UPDATE resetpasswordtokens SET isUsed = TRUE WHERE token = ?";

        dbConnect.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, token);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Lỗi cập nhật trạng thái token: " + e.getMessage());
            }
            return null;
        });
    }

    // Đổi mật khẩu trong trang cá nhân
    public boolean changePassword(String userName, String passWord) {
        return JDBIConnector.get().withHandle(handle -> {
            String sql = "UPDATE users SET passWord = ? WHERE userName = ?";
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, passWord);
                ps.setString(2, userName);
                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0; // Trả về true nếu có ít nhất 1 hàng được cập nhật
            } catch (SQLException e) {
                e.printStackTrace();
                return false; // Trả về false nếu có lỗi
            }
        });
    }

    public String getPassWordByUserName(String userName) {
        String sql = "SELECT passWord FROM users WHERE userName = ?";

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

}