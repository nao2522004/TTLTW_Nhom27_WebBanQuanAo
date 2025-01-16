//package vn.edu.hcmuaf.fit.webbanquanao.service;
//
//import jakarta.mail.*;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;
//import vn.edu.hcmuaf.fit.webbanquanao.dao.UserDao;
//
//import java.util.Properties;
//
//public class ForgotPasswordService {
//    private UserDao userDao;
//
//    public ForgotPasswordService() {
//        this.userDao = new UserDao();
//    }
//
//    public boolean sendResetPasswordLink(String email) {
//        if (email == null || email.isEmpty()) {
//            throw new IllegalArgumentException("Email không thể là null hoặc rỗng");
//        }
//
//        if (!userDao.isEmailExist(email)) {
//            throw new IllegalArgumentException("Email không tồn tại trong hệ thống.");
//        }
//
//        final String from = "nam784412@gmail.com";
//        final String password = "xjbx pard uimo eyth";
//
//        Properties properties = new Properties();
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "587");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
//
//        Session session = Session.getInstance(properties, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(from, password);
//            }
//        });
//
//        try {
//            String resetLink = "http://localhost:8080/WebBanQuanAo/reset-password.jsp?email=" + email;
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(from));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
//            message.setSubject("Đặt lại mật khẩu của bạn");
//            message.setText("Vui lòng nhấp vào liên kết sau để đặt lại mật khẩu của bạn: " + resetLink);
//            Transport.send(message);
//            return true; // Đã gửi email thành công
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            return false; // Gửi email thất bại
//        }
//    }
//}