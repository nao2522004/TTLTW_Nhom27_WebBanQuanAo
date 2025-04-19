package vn.edu.hcmuaf.fit.webbanquanao.admin.service;

import vn.edu.hcmuaf.fit.webbanquanao.admin.dao.AUserDao;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUser;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUserRolePermission;


import java.util.*;


public class AUserService {

    public Map<String, AUser> showUser() {
        AUserDao uDao = new AUserDao();
        return uDao.listUser;
    }

    public boolean updateUser(AUser user, String userName) {
        AUserDao uDao = new AUserDao();
        return uDao.update(user, getUserByUsername(userName).getUserName());
    }

//    public boolean createUser(AUser user) {
//        AUserDao uDao = new AUserDao();
//        if(uDao.listUser.containsKey(user.getUserName()))
//            return false;
//        return uDao.create(user);
//    }

    public AUser getUserByUsername(String username) {
        AUserDao uDao = new AUserDao();
        if (!uDao.listUser.containsKey(username))
            return null;
        return uDao.listUser.get(username);
    }

    public Map<String, AUserRolePermission> getUserRolePermissionByUsername(String username) {
        AUserDao uDao = new AUserDao();
        if (!uDao.listUser.containsKey(username))
            return null;
        return uDao.getRolePermission(username);
    }


    public boolean deleteUser(String username) {
        AUserDao uDao = new AUserDao();
        if (!uDao.listUser.containsKey(username)) return false;
        AUser user = uDao.listUser.get(username);
        if (user.getStatus() == 4) return false;
        boolean isDeletedInDB = uDao.delete(username, 4);
        if (isDeletedInDB) user.setStatus(4);
        return isDeletedInDB;
    }

    public List<Integer> getRoleIdsByRoleName(List<String> roleNames) {
        AUserDao uDao = new AUserDao();
        if (roleNames == null || roleNames.isEmpty()) return null;
        return uDao.getRoleIdByRoleName(roleNames);
    }

    public List<String> getRoleNameByUserName(String username) {
        AUserDao uDao = new AUserDao();
        if (!uDao.listUser.containsKey(username)) return null;
        return uDao.getRoleNameByUserName(username);
    }

    public void ManageUserRolesAndPermissions(String userName, List<String> newRolesName, Map<String, Integer> newPermissions) {
        AUserDao uDao = new AUserDao();
        if (!uDao.listUser.containsKey(userName)) return;
        AUser user = uDao.listUser.get(userName);
        if (user.getStatus() == 4) return;

        ManageUserRoles(userName, newRolesName);

        ManageUserPermissions(userName, newPermissions);
    }


    private void ManageUserRoles(String userName, List<String> newRolesName) {
        AUserDao uDao = new AUserDao();

        // Lấy danh sách role hiện tại từ DB
        List<String> currentRoleNames = uDao.getRoleNameByUserName(userName);

        // Dùng Set để so sánh
        Set<String> currentRolesSet = new HashSet<>(currentRoleNames);
        Set<String> newRolesSet = new HashSet<>(newRolesName);

        // Các role cần thêm
        Set<String> rolesToAdd = new HashSet<>(newRolesSet);
        rolesToAdd.removeAll(currentRolesSet);

        // Các role cần xóa
        Set<String> rolesToRemove = new HashSet<>(currentRolesSet);
        rolesToRemove.removeAll(newRolesSet);

        // Convert role name -> role id
        List<Integer> roleIdsToAdd = uDao.getRoleIdByRoleName(new ArrayList<>(rolesToAdd));
        List<Integer> roleIdsToRemove = uDao.getRoleIdByRoleName(new ArrayList<>(rolesToRemove));

        // Thêm và xóa role
        uDao.addUserRoles(roleIdsToAdd, userName);
        uDao.removeUserRoles(roleIdsToRemove, userName);
    }

    private void ManageUserPermissions(String userName, Map<String, Integer> newPermissions) {
       AUserDao uDao = new AUserDao();
       uDao.updateUserPermissions(userName, newPermissions);
    }


    public static void main(String[] args) {
        AUserService userService = new AUserService();

//        System.out.println(userService.showUser());
//
//        System.out.println(userService.getUserRolePermissionByUsername("admin"));

//         userService.ManageUserRoles("user1", Arrays.asList("USER", "STAFF"));

        userService.ManageUserPermissions("user1", new HashMap<String, Integer>() {{;
            put("Admin", 1);
            put("Cart", 1);
            put("Order", 1);
            put("Payment", 1);
            put("Product", 1);
            put("User", 1);
        }});
    }

}
