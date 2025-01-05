package vn.edu.hcmuaf.fit.webbanquanao.dao;

import vn.edu.hcmuaf.fit.webbanquanao.dao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class UserDao {
    public static Map<String, User> listUser = getAllUser();

    public UserDao() {

    }

    public static Map<String, User> getAllUser() {
        Map<String, User> users = new HashMap<>();
        String sql = "SELECT * FROM users ORDER BY id DESC";

        JDBIConnector.get().withHandle(handle -> {
            try (ResultSet rs = handle.getConnection().createStatement().executeQuery(sql)) {
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
                    user.setCreatedAt(rs.getDate("createAt").toLocalDate().atStartOfDay());
                    user.setRoleId(rs.getInt("roleId"));
                    users.put(user.getUserName(), user);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return users;
        });
        return users;
    }


}
