package vn.edu.hcmuaf.fit.webbanquanao.service;

import vn.edu.hcmuaf.fit.webbanquanao.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.model.User;

import java.util.Map;


public class UserService {

    public Map<String, User> showUser() {
        UserDao uDao = new UserDao();
        return uDao.listUser;
    }

    public boolean updateUser(User user, String userName) {
        UserDao uDao = new UserDao();
        return uDao.update(user, getUserByUsername(userName).getUserName());
    }

    public boolean createUser(User user) {
        UserDao uDao = new UserDao();
        if(uDao.listUser.containsKey(user.getUserName()))
            return false;
        return uDao.create(user);
    }

    public User getUserByUsername(String username) {
        UserDao uDao = new UserDao();
        if(!uDao.listUser.containsKey(username))
            return null;
        return uDao.listUser.get(username);
    }

    public boolean deleteUser(String username) {
        UserDao uDao = new UserDao();
        if(uDao.listUser.containsKey(username))
            return uDao.delete(username);
        return false;
    }


    public static void main(String[] args) {
        UserService userService = new UserService();

        System.out.println(userService.showUser());

        System.out.println(userService.getUserByUsername("user2"));
    }

}
