
package vn.edu.hcmuaf.fit.webbanquanao.dao;

import vn.edu.hcmuaf.fit.webbanquanao.dao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.User;

public class RoleDao {

    public User findByUserNameAndPasswordAndStatus() {
        return new JDBIConnector().getJdbi()
                .withHandle(handle ->
                        handle.createQuery("SELECT * FROM roles")
                                .mapToBean(User.class)
                                .findFirst()
                                .orElse(null)
                );
    }

}
