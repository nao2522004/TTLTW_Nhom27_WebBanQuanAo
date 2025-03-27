package vn.edu.hcmuaf.fit.webbanquanao.admin.service;

import vn.edu.hcmuaf.fit.webbanquanao.admin.dao.AUserDao;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUser;


import java.util.Map;


public class AUserService {

    public Map<String, AUser> showUser() {
        AUserDao uDao = new AUserDao();
        return uDao.listUser;
    }

//    public boolean updateUser(AUser user, String userName) {
//        AUserDao uDao = new AUserDao();
//        return uDao.update(user, getUserByUsername(userName).getUserName());
//    }
//
//    public boolean createUser(AUser user) {
//        AUserDao uDao = new AUserDao();
//        if(uDao.listUser.containsKey(user.getUserName()))
//            return false;
//        return uDao.create(user);
//    }

    public AUser getUserByUsername(String username) {
        AUserDao uDao = new AUserDao();
        if(!uDao.listUser.containsKey(username))
            return null;
        return uDao.listUser.get(username);
    }

//    public boolean deleteUser(String username) {
//        AUserDao uDao = new AUserDao();
//        if(!uDao.listUser.containsKey(username)) return false;
//        AUser user = uDao.listUser.get(username);
//        if(user.getStatus() == 4) return false;
//        boolean isDeletedInDB = uDao.delete(username, 4);
//        if(isDeletedInDB) user.setStatus(4);
//        return isDeletedInDB;
//    }

//    public Integer getRoleIdByUserName(String username) {
//        AUserDao uDao = new AUserDao();
//        return uDao.listUser.get(username).getRoleId();
//    }


    public static void main(String[] args) {
        AUserService userService = new AUserService();

        System.out.println(userService.showUser());
    }



}
