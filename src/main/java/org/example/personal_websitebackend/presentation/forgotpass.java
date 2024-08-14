package org.example.personal_websitebackend.presentation;

import jakarta.persistence.Table;
import org.springframework.stereotype.Component;

@Component
@Table(name = "register")
public class forgotpass {
    private String email;

    public forgotpass(){

    }
    public forgotpass(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
