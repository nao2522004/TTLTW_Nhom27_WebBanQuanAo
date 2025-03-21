package vn.edu.hcmuaf.fit.webbanquanao.user.auth.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;


public class EmailService {
    private static final String EMAIL_FROM = "nam784412@gmail.com";
    private static final String PASSWORD = "kxto gjht fjyd xumr";

    public static void sendEmail(String recipient, String subject, String otp) {
        if (recipient == null || recipient.isEmpty()) {
            System.out.println("Lỗi: Địa chỉ email trống!");
            return;
        }

        String body = "Xin chào,\n\n"
                + "Mã OTP của bạn là: " + otp + "\n\n"
                + "Mã này có hiệu lực trong 5 phút. Nếu bạn không yêu cầu, vui lòng bỏ qua email này.\n\n"
                + "Trân trọng,\nĐội ngũ hỗ trợ.";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email đã gửi thành công tới: " + recipient);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Lỗi gửi email: " + e.getMessage());
        }
    }

}

