package vn.edu.hcmuaf.fit.webbanquanao.service;

import vn.edu.hcmuaf.fit.webbanquanao.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.User;

public class AuthService {

    public User checkLogin(String username, String password) {
        UserDao userDao = new UserDao();
        User user = userDao.findUserName(username);
        if (user != null && password != null && user.getPassword().equals(password)) {
            user.setPassword(null);
            return user;
        }
        return null;
    }
}
