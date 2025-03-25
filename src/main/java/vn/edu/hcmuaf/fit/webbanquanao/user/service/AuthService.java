package vn.edu.hcmuaf.fit.webbanquanao.user.service;

import org.mindrot.jbcrypt.BCrypt;

import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;

import java.util.List;

public class AuthService {
    private final UserDao aUserDao = new UserDao(); // Quản lý danh sách user
    private final UserDao userDao = new UserDao(); // Làm việc với database

    public User checkLogin(String userName, String inputPassword) {
        // Lấy user từ danh sách
        User user = aUserDao.listUser.get(userName);

        if (user == null) return null; // Không tìm thấy người dùng

        // Lấy mật khẩu từ database
        String storedPassword = userDao.getPassWordByUserName(userName);
        if (storedPassword == null) return null; // Nếu mật khẩu không tồn tại

        boolean isHashed = storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$");

        // Kiểm tra mật khẩu
        if (isHashed) {
            // Nếu mật khẩu đã mã hóa bằng BCrypt
            if (!BCrypt.checkpw(inputPassword, storedPassword)) {
                return null; // Sai mật khẩu
            }
        } else {
            // Nếu mật khẩu chưa mã hóa, so sánh trực tiếp
            if (!storedPassword.equals(inputPassword)) {
                return null; // Sai mật khẩu
            }
            // Cập nhật mật khẩu sang dạng BCrypt nếu đúng
            String hashedPassword = BCrypt.hashpw(inputPassword, BCrypt.gensalt(12));
            userDao.changePassword(userName, hashedPassword);
        }

        // Lấy vai trò và quyền
        String roleName = userDao.getRoleNameByUserName(userName);
        List<String> permissions = userDao.getPemissionNameByUserName(userName);

        // Cập nhật thông tin vai trò và quyền vào đối tượng người dùng
        user.setRoleName(roleName);
        user.setPermissionName(permissions);

        user.setPassWord(null); // Xóa mật khẩu trước khi trả về
        return user;
    }
}


