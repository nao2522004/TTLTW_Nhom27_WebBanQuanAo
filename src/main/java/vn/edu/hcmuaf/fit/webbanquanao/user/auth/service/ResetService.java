package vn.edu.hcmuaf.fit.webbanquanao.user.auth.service;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.UUID;

public class ResetService {
    private final int LIMIT_MINUS = 10;
    private final String from = "nam784412@gmail.com";
    private final String password = "eqyo xxaf omly mtog";
    public String genarateToken() {
        return UUID.randomUUID().toString();
    }
    public LocalDateTime expiresAt() {
        return LocalDateTime.now().plusMinutes(LIMIT_MINUS);
    }
    public boolean isExpired(LocalDateTime time) {
        return LocalDateTime.now().isAfter(time);
    }
    public boolean sendEmail(String to, String link, String name) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        MimeMessage message = new MimeMessage(session);
        try {
            message.addHeader("Content-Type", "text/html;charset=UTF-8");
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            message.setSubject("Reset Password");
            String content =  "<h1>Hello " + name + "</h1>" +
                    "<p>Click the link to reset password<a href=" + link + ">Click here</a></p>";
            message.setContent(content, "text/html;charset=UTF-8");
            Transport.send(message);
            System.out.println("Send Successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Send error");
            return false;
        }
    }
}
