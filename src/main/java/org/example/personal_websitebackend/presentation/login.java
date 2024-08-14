package org.example.personal_websitebackend.presentation;

import jakarta.persistence.Table;
import org.springframework.stereotype.Component;

@Component
@Table(name = "register")
public class login {
    private String email;
    private String password;


    public login(){}



    public login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
