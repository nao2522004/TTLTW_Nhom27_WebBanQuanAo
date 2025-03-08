package vn.edu.hcmuaf.fit.webbanquanao.user.service;

import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;

public class UserService {

    public boolean changePasswordUser(String userName, String currentPassWord, String newPassWord) {
        UserDao user = new UserDao();
        if(!user.listUser.containsKey(userName)) return false;

        String storedPassword = user.getPassWordByUserName(userName);

        if (!storedPassword.equals(currentPassWord)) return false;

        return user.changePassword(userName, newPassWord);
    }

}
