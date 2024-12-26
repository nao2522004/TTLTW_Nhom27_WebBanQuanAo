package vn.edu.hcmuaf.fit.webbanquanao.dao;

import vn.edu.hcmuaf.fit.webbanquanao.dao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.User;

public class UserDao {

    public User findByUserNameAndPasswordAndStatus(String username, String password, Integer status) {
        return new JDBIConnector().getJdbi()
                .withHandle(handle ->
                        handle.createQuery("SELECT u.*, r.role_name " +
                                        "FROM users u " +
                                        "JOIN roles r ON u.role_id = r.roleid " +
                                        "WHERE u.username = :username AND u.password = :password AND u.status = :status")
                                .bind("username", username)
                                .bind("password", password)
                                .bind("status", status)
                                .mapToBean(User.class)  // Chuyển kết quả thành đối tượng User
                                .findFirst()  // Lấy kết quả đầu tiên
                                .orElse(null)  // Nếu không có kết quả thì trả về null
                );
    }


}
