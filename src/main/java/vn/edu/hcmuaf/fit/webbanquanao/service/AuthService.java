package vn.edu.hcmuaf.fit.webbanquanao.service;

import vn.edu.hcmuaf.fit.webbanquanao.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.model.User;

import java.util.Map;

public class AuthService {

    public User checkLogin(String userName, String passWord) {
        UserDao uDao = new UserDao();

        User user = uDao.listUser.get(userName);

        if (user != null) {
            if (user.getPassWord().equals(passWord)) {
                user.setPassWord(null);
                return user;
            }
        }
        return null;
    }

}
