package vn.edu.hcmuaf.fit.webbanquanao.admin.service;

import vn.edu.hcmuaf.fit.webbanquanao.admin.dao.AUserDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.util.Map;


public class AUserService {

    public Map<String, User> showUser() {
        AUserDao uDao = new AUserDao();
        return uDao.listUser;
    }

    public boolean updateUser(User user, String userName) {
        AUserDao uDao = new AUserDao();
        return uDao.update(user, getUserByUsername(userName).getUserName());
    }

    public boolean createUser(User user) {
        AUserDao uDao = new AUserDao();
        if(uDao.listUser.containsKey(user.getUserName()))
            return false;
        return uDao.create(user);
    }

    public User getUserByUsername(String username) {
        AUserDao uDao = new AUserDao();
        if(!uDao.listUser.containsKey(username))
            return null;
        return uDao.listUser.get(username);
    }

    public boolean deleteUser(String username) {
        AUserDao uDao = new AUserDao();
        if(!uDao.listUser.containsKey(username)) return false;
        User user = uDao.listUser.get(username);
        if(user.getStatus() == 4) return false;
        boolean isDeletedInDB = uDao.delete(username, 4);
        if(isDeletedInDB) user.setStatus(4);
        return isDeletedInDB;
    }

    public Integer getRoleIdByUserName(String username) {
        AUserDao uDao = new AUserDao();
        return uDao.listUser.get(username).getRoleId();
    }


    public static void main(String[] args) {
        AUserService userService = new AUserService();

        System.out.println(userService.showUser());

        System.out.println(userService.getUserByUsername("user2"));
    }



}
