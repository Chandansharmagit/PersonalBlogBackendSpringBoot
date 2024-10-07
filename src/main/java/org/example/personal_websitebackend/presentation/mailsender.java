package org.example.personal_websitebackend.presentation;


import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Component;

@Component
public class mailsender {
    private String name;
    private String email;
    private String text;

    public mailsender(){}

    public mailsender(String name, String text, String email) {
        this.name = name;
        this.text = text;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
