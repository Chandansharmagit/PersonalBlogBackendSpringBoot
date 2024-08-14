package org.example.personal_websitebackend.presentation;


import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "chandanPort")
public class chandanPortfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String text;

    public chandanPortfolio() {

    }

    public chandanPortfolio(int id, String text, String email, String name) {
        this.id = id;
        this.text = text;
        this.email = email;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
