package vn.edu.hcmuaf.fit.webbanquanao.dao;

import org.mindrot.jbcrypt.BCrypt;
import vn.edu.hcmuaf.fit.webbanquanao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.model.TokenForgotPassword;
import vn.edu.hcmuaf.fit.webbanquanao.model.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {

    JDBIConnector dbConnect;

    public UserDao() {
        dbConnect = new JDBIConnector();
    }
    // Đăng ký người dùng mới (mã hóa mật khẩu)
    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (avatar, password, fullName, gmail, phone, address, notificationCheck, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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

    public void updatePassword(String email, String password) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";

        dbConnect.get().withHandle(handle -> {
            try (Connection conn = handle.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, password);
                ps.setString(2, email);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e);
            }
            return null;
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
                        user.setEmail(rs.getString("email"));
                        return user; // Trả về đối tượng User khi tìm thấy
                    }
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy thông tin người dùng: " + e.getMessage());
                e.printStackTrace(); // In chi tiết lỗi để debug
            }
            return null; // Trả về null nếu không tìm thấy hoặc xảy ra lỗi
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
            return null; // Trả về null nếu không tìm thấy hoặc xảy ra lỗi
        });
    }

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

}