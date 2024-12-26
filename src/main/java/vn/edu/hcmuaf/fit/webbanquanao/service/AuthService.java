package vn.edu.hcmuaf.fit.webbanquanao.service;

import vn.edu.hcmuaf.fit.webbanquanao.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.User;

public class AuthService {

    public User checkLogin(String username, String password, Integer status) {
        UserDao userDao = new UserDao();
        User user = userDao.findByUserNameAndPasswordAndStatus(username, password, status);
        if (user != null && user.getPassword().equals(password) && status == 1) {
            user.setPassword(null);
            return user;
        }
        return null;
    }
}
