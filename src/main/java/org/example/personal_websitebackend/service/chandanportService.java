package org.example.personal_websitebackend.service;

import org.example.personal_websitebackend.presentation.chandanPortfolio;
import org.example.personal_websitebackend.presentation.mailsender;
import org.example.personal_websitebackend.repository.chandanportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class chandanportService implements chandanportData {

    @Autowired
    private chandanportRepo chandanportRepo;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Override
    public chandanPortfolio saveChandanPortfolio(chandanPortfolio chandanPortfolio) {
        return chandanportRepo.save(chandanPortfolio);
    }

    public void sendMail(String recipientEmail, mailsender mailsender) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(recipientEmail);
        message.setSubject("A New Message from Client");
        // Format the email content
        String emailContent = String.format(
                "Name: %s%nEmail: %s%nMessage: %s",
                mailsender.getName(),
                mailsender.getEmail(),
                mailsender.getText()
        );
        message.setText(emailContent);

        mailSender.send(message);
    }
}
