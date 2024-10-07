package org.example.personal_websitebackend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.personal_websitebackend.presentation.mailsender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class mailService {

    private final JavaMailSender mailSenderr;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Autowired
    public mailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSenderr = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendMail(mailsender mailSender) {
        try {
            // Prepare Thymeleaf context
            Context context = new Context();
            context.setVariable("name", mailSender.getName());
            context.setVariable("email", mailSender.getEmail());
            context.setVariable("message", mailSender.getText());

            // Process template
            String emailContent = templateEngine.process("email-template", context);

            // Create and configure MimeMessage
            MimeMessage mimeMessage = mailSenderr.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true); // true for multipart
            messageHelper.setFrom(fromMail);
            messageHelper.setTo(mailSender.getEmail());
            messageHelper.setTo("utamsharma57@gmail.com");
            messageHelper.setSubject("A New Message from Client");
            messageHelper.setText(emailContent, true); // true for HTML content

            // Send the email
            mailSenderr.send(mimeMessage);
        } catch (MessagingException e) {
            // Handle errors in email sending
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }


    //sending the email to reset password

    public void sendResetPasswordEmail(String to, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, please click the following link: " + resetLink);
        mailSenderr.send(message);
    }


}
