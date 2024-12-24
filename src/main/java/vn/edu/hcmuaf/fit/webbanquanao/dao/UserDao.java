package vn.edu.hcmuaf.fit.webbanquanao.dao;

import vn.edu.hcmuaf.fit.webbanquanao.dao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.User;

public class UserDao {

    public User findUserName(String username){
        return new JDBIConnector().getJdbi()
                .withHandle(handle ->
                        handle.createQuery("SELECT * FROM users WHERE username = :username")
                                .bind("username", username)
                                .mapToBean(User.class)
                                .findFirst()
                                .orElse(null)
                );
    }
}
