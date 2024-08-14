package org.example.personal_websitebackend.service;

import org.example.personal_websitebackend.presentation.mailsender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class mailService {
    @Autowired
    private static JavaMailSender mailersender;
    @Value("${spring.mail.username}")
    private static String fromMail;
    public static void sendMail(mailsender mailSender) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(mailSender.getEmail());
        message.setSubject("A New Message from Client");
        String emailContent = String.format(
                "Name: %s%nEmail: %s%nMessage: %s",
                mailSender.getName(),
                mailSender.getEmail(),
                mailSender.getText()
        );
        message.setText(emailContent);
        mailersender.send(message);
    }


}

