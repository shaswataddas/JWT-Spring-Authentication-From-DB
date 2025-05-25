package com.jwt.authentication.jwtAuthentication.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSenderUtil {
    private static JavaMailSender javaMailSender;

    public MailSenderUtil(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public static void sendEmail(String userEmail, String subject, String msgBody){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject(subject);
        message.setText(msgBody);

        javaMailSender.send(message);
    }
}
