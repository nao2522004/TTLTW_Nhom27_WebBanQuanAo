package vn.edu.hcmuaf.fit.webbanquanao.user.service;

import org.mindrot.jbcrypt.BCrypt;
import vn.edu.hcmuaf.fit.webbanquanao.admin.dao.AUserDao;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUser;
import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;

public class AuthService {

    public AUser checkLogin(String userName, String inputPassword) {
        AUserDao aUserDao = new AUserDao(); // Quản lý danh sách user
        UserDao userDao = new UserDao(); // Làm việc với database

        // Lấy User từ danh sách user
        AUser user = aUserDao.listUser.get(userName);

        if (user != null) {
            // Lấy mật khẩu từ database
            String storedPassword = userDao.getPassWordByUserName(userName);

            if (storedPassword == null) {
                return null; // Nếu mật khẩu không tồn tại, trả về null
            }

            boolean isHashed = storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$");

            if (isHashed) {
                // Nếu mật khẩu đã mã hóa, kiểm tra bằng BCrypt
                if (BCrypt.checkpw(inputPassword, storedPassword)) {
                    user.setPassWord(null); // Xóa mật khẩu trước khi trả về
                    return user;
                } else {
                    return null; // Sai mật khẩu
                }
            } else {
                // Nếu mật khẩu chưa mã hóa, kiểm tra trực tiếp
                if (storedPassword.equals(inputPassword)) {
                    // Nếu đúng, cập nhật sang BCrypt
                    System.out.println("Updating old password to hashed version...");
                    String hashedPassword = BCrypt.hashpw(inputPassword, BCrypt.gensalt(12));
                    userDao.changePassword(userName, hashedPassword); // Cập nhật mật khẩu mới vào database
                    user.setPassWord(null);
                    return user;
                } else {
                    return null; // Sai mật khẩu
                }
            }
        }
        return null; // Người dùng không tồn tại
    }



}
