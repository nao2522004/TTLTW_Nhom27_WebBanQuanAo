package vn.edu.hcmuaf.fit.webbanquanao.user.service;

import org.mindrot.jbcrypt.BCrypt;
import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;

public class UserService {
    private final UserDao userDao = new UserDao();

    public boolean changePasswordUser(String userName, String currentPassWord, String newPassWord) {
        // Kiểm tra user có tồn tại không
        String storedPassword = userDao.getPassWordByUserName(userName);
        if (storedPassword == null) {
            System.out.println("User not found!");
            return false;
        }

        // Kiểm tra mật khẩu cũ có đúng không
        boolean isOldPasswordHashed = storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$");

        if (isOldPasswordHashed) {
            if (!BCrypt.checkpw(currentPassWord, storedPassword)) {
                System.out.println("Incorrect current password!");
                return false;
            }
        } else {
            if (!storedPassword.equals(currentPassWord)) {
                System.out.println("Incorrect current password (not hashed)!");
                return false;
            }
            // Cập nhật mật khẩu cũ sang dạng mã hóa BCrypt
            System.out.println("Updating old password to hashed version...");
            storedPassword = BCrypt.hashpw(currentPassWord, BCrypt.gensalt(12));
            userDao.changePassword(userName, storedPassword);
        }

        // Mã hóa mật khẩu mới
        String hashedNewPassword = BCrypt.hashpw(newPassWord, BCrypt.gensalt(12));
        return userDao.changePassword(userName, hashedNewPassword);
    }
}