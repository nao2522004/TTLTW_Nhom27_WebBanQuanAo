package vn.edu.hcmuaf.fit.webbanquanao.dao;

import vn.edu.hcmuaf.fit.webbanquanao.dao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserDao {

    public User findByUserNameAndPasswordAndStatus(String userName, String passWord, Integer status) {
        return new JDBIConnector().getJdbi()
                .withHandle(handle ->
                        handle.createQuery("SELECT u.*, r.roleName " +
                                        "FROM users u " +
                                        "JOIN roles r ON u.roleId = r.id " +
                                        "WHERE u.userName = :userName AND u.passWord = :passWord AND u.status = :status")
                                .bind("userName", userName)
                                .bind("passWord", passWord)
                                .bind("status", status)
                                .mapToBean(User.class)  // Chuyển kết quả thành đối tượng User
                                .findFirst()  // Lấy kết quả đầu tiên
                                .orElse(null)  // Nếu không có kết quả thì trả về null
                );
    }




}
