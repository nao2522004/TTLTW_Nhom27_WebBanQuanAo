package vn.edu.hcmuaf.fit.webbanquanao.service;

import vn.edu.hcmuaf.fit.webbanquanao.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.model.User;

import java.util.Map;

public class AuthService {

    public User checkLogin(String userName, String passWord) {
        UserDao uDao = new UserDao(); // Khởi tạo UserDao

        // Lấy User từ cơ sở dữ liệu thông qua UserDao
        User user = uDao.listUser.get(userName);

        if (user != null) {
            // So sánh mật khẩu
            if (user.getPassWord().equals(passWord)) {
                user.setPassWord(null); // Xóa mật khẩu trước khi trả về
                return user;
            }
        }
        return null; // Trả về null nếu không tìm thấy hoặc sai mật khẩu
    }

}
