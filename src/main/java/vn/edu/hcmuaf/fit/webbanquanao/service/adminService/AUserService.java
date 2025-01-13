package vn.edu.hcmuaf.fit.webbanquanao.service.adminService;

import vn.edu.hcmuaf.fit.webbanquanao.dao.adminDAO.AUserDao;

import java.util.Map;


public class AUserService {

    public Map<String, vn.edu.hcmuaf.fit.webbanquanao.model.User> showUser() {
        AUserDao uDao = new AUserDao();
        return uDao.listUser;
    }

    public boolean updateUser(vn.edu.hcmuaf.fit.webbanquanao.model.User user, String userName) {
        AUserDao uDao = new AUserDao();
        return uDao.update(user, getUserByUsername(userName).getUserName());
    }

    public boolean createUser(vn.edu.hcmuaf.fit.webbanquanao.model.User user) {
        AUserDao uDao = new AUserDao();
        if(uDao.listUser.containsKey(user.getUserName()))
            return false;
        return uDao.create(user);
    }

    public vn.edu.hcmuaf.fit.webbanquanao.model.User getUserByUsername(String username) {
        AUserDao uDao = new AUserDao();
        if(!uDao.listUser.containsKey(username))
            return null;
        return uDao.listUser.get(username);
    }

    public boolean deleteUser(String username) {
        AUserDao uDao = new AUserDao();
        if(uDao.listUser.containsKey(username))
            return uDao.delete(username);
        return false;
    }


    public static void main(String[] args) {
        AUserService userService = new AUserService();

        System.out.println(userService.showUser());

        System.out.println(userService.getUserByUsername("user2"));
    }

}
