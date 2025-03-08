package vn.edu.hcmuaf.fit.webbanquanao.user.auth.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailUtil {

    // Gửi email với liên kết reset mật khẩu
    public void sendResetPasswordLink(String email) {
        String host = "smtp.gmail.com";
        final String fromEmail = "nhannghialai@gmail.com";
        final String password = "xpna sqpq sefp dfix";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Tạo Session với thông tin đăng nhập của người gửi
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // Tạo liên kết reset mật khẩu
            String resetLink = "http://localhost:8080/WebBanQuanAo/reset-password.jsp";

            // Tạo đối tượng email
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Đặt lại mật khẩu của bạn");
            message.setText("Vui lòng nhấp vào liên kết sau để đặt lại mật khẩu của bạn: " + resetLink);

            // Gửi email
            Transport.send(message);
            System.out.println("Email đã được gửi thành công");

        } catch (MessagingException e) {
            System.out.println("Gửi email thất bại: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void reponeContact(String toEmail, String subject, String body) {
        // Cấu hình email
        String host = "smtp.gmail.com";
        final String fromEmail = "nhannghialai@gmail.com";
        final String password = "xpna sqpq sefp dfix";


        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Xác thực tài khoản email
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        // Soạn email
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendEmail(String toEmail, String subject, String body) {
        String fromEmail = "nhannghialai@gmail.com";  // Thay bằng email của bạn
        String password = "xpna sqpq sefp dfix";  // Thay mật khẩu đúng vào đây

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email đã được gửi thành công.");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Không thể gửi email", e);
        }
    }

}
