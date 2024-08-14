package org.example.personal_websitebackend.presentation.userRegister;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "register")
public class register {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;




    public register(){

    }
    public register(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;

        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
