package vn.edu.hcmuaf.fit.webbanquanao.dao;

import vn.edu.hcmuaf.fit.webbanquanao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserDao implements CRUIDDao {

    public Map<String, User> listUser;

    public UserDao() {
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

    @Override
    public boolean create(Object obj) {
        User user = (User) obj;
        listUser.put(user.getUserName(), user);
        String sql = "INSERT INTO users (userName, password, firstName, lastName, email, avatar, address, phone, status, roleId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return JDBIConnector.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getPassWord());
                ps.setString(3, user.getFirstName());
                ps.setString(4, user.getLastName());
                ps.setString(5, user.getEmail());
                ps.setString(6, user.getAvatar());
                ps.setString(7, user.getAddress());
                ps.setInt(8, user.getPhone());
                ps.setInt(9, user.getStatus());
                ps.setInt(10, user.getRoleId());
                return ps.executeUpdate() > 0;
            } catch (Exception e) {
                System.out.println("Loi khi tạo user: " + e.getMessage());
            }
            return false;
        });
    }

    @Override
    public boolean update(Object obj, String userName) {
        return JDBIConnector.get().withHandle(handle -> {
            User user = (User) obj;
            listUser.replace(userName, user);
            String sql = "UPDATE users SET id = ?, userName = ?, firstName = ?, lastName = ?, email = ?, avatar = ?, address = ?, phone = ?, status = ?, createdAt = ?, roleId = ? WHERE userName = ?";
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setInt(1, user.getId());
                ps.setString(2, user.getUserName());
                ps.setString(3, user.getFirstName());
                ps.setString(4, user.getLastName());
                ps.setString(5, user.getEmail());
                ps.setString(6, user.getAvatar());
                ps.setString(7, user.getAddress());
                ps.setInt(8, user.getPhone());
                ps.setInt(9, user.getStatus());
                ps.setDate(10, java.sql.Date.valueOf(user.getCreatedAt().toLocalDate()));
                ps.setInt(11, user.getRoleId());
                ps.setString(12, userName);
                return ps.executeUpdate() > 0;
            } catch (Exception e) {
                System.out.println("Loi khi update user: " + e.getMessage());
            }
            return false;
        });
    }


    @Override
    public boolean delete(String userName) {
        listUser.remove(userName);
        return JDBIConnector.get().withHandle(handle -> {
            String sql = "DELETE FROM users WHERE userName = '" + userName + "'";
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, userName);
                return ps.executeUpdate() > 0;
            } catch (Exception e) {
                System.out.println("Loi khi xoa user: " + e.getMessage());
            }
            return false;
        });
    }


}
