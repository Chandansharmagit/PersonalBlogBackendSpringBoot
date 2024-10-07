package org.example.personal_websitebackend.Controller;

import org.example.personal_websitebackend.configuration.JwtBlacklistService;
import org.example.personal_websitebackend.configuration.JwtUtil;
import org.example.personal_websitebackend.presentation.*;
import org.example.personal_websitebackend.presentation.resetingPassorrd.PasswordResetToken;
import org.example.personal_websitebackend.presentation.userRegister.register;
import org.example.personal_websitebackend.repository.PasswordResetTokenRepository;
import org.example.personal_websitebackend.repository.registerRepo.registerRepo;
import org.example.personal_websitebackend.service.chandanportData;
import org.example.personal_websitebackend.service.emailsaving_data;
import org.example.personal_websitebackend.service.mailService;
import org.example.personal_websitebackend.service.registerService.registerService;
import org.example.personal_websitebackend.service.registerService.registerdata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin
public class PublicController {

    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private chandanportData chandanportData;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private mailService mailService;

    @Autowired
    private registerdata registerdata;

    @Autowired
    private registerRepo registerRepo;

    @Autowired
    private registerService registerService;

    @Autowired
    private emailsaving_data emailsaving_data;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JwtBlacklistService jwtBlacklistService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private org.example.personal_websitebackend.presentation.mailsender mailsender;

    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/ch")
    public String ch() {
        return "Hello World";
    }

    @PostMapping("/sending_email")
    public ResponseEntity<String> updateData(@RequestBody chandanPortfolio chandanPortfolio) {
        try {
            chandanportData.saveChandanPortfolio(chandanPortfolio);
            mailsender mailsender = new mailsender();
            mailsender.setName(chandanPortfolio.getName());
            mailsender.setEmail(chandanPortfolio.getEmail());
            mailsender.setText(chandanPortfolio.getText());
            mailService.sendMail(mailsender);
            return ResponseEntity.ok("Data saved and email sent successfully!");
        } catch (Exception e) {
            logger.error("Error saving data or sending email", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving data or sending email: " + e.getMessage());
        }
    }

    @PostMapping("/mail_sending")
    public ResponseEntity<String> sendMail(@RequestBody mailsender mailsender) {
        try {
            mailService.sendMail(mailsender);
            return ResponseEntity.ok("Email sent");
        } catch (Exception e) {
            logger.error("Error sending email", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<register> saving(@RequestBody register register) {
        try {
            register savedUser = registerdata.saving_user(register);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            logger.error("Error registering user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/finding")
    public ResponseEntity<Map<String, String>> login(@RequestBody login login) {
        try {
            // Find the user by email
            register user = registerRepo.findByEmail(login.getEmail());

            // If user exists and the password matches the hashed password
            if (user != null && passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                // Generate JWT token
                String token = jwtUtil.generateToken(login.getEmail());

                // Update the user record with the new token
                user.setToken(token);
                registerRepo.save(user);

                // Prepare the response body
                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("token", token);
                responseBody.put("email", login.getEmail());
                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            } else {
                // Return unauthorized if email or password is invalid
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid email or password"));
            }
        } catch (Exception e) {
            logger.error("Error during login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error logging in: " + e.getMessage()));
        }
    }

    @GetMapping("/free")
    public ResponseEntity<String> publicUser() {
        return ResponseEntity.ok("Yes, I am a public user");
    }

    @GetMapping("/normal")
    public ResponseEntity<String> normalUser() {
        return ResponseEntity.ok("Yes, I am a normal user");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> adminUser() {
        return ResponseEntity.ok("Yes, I am an admin user");
    }

    @PostMapping("/saving_email")
    public ResponseEntity<EmailSaving> saveEmail(@RequestBody EmailSaving emailSaving) {
        try {
            EmailSaving savedEmail = emailsaving_data.emailsaving(emailSaving);
            return ResponseEntity.ok(savedEmail);
        } catch (Exception e) {
            logger.error("Error saving email", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/finding_user")
    public ResponseEntity<List<register>> getAllUsers() {
        List<register> users = registerService.findall();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/finding_user_by_email")
    public ResponseEntity<register> getUserByEmail(@RequestParam String email) {
        register user = registerService.getUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<String> logout(@RequestBody String email) {
        register user = registerRepo.findByEmail(email);
        if (user != null) {
            // Clear the token from the database
            user.setToken(null);
            registerRepo.save(user);

            // Optionally, you can also add the token to the blacklist here if required
            // jwtBlacklistService.addTokenToBlacklist(user.getToken());

            return ResponseEntity.ok("User logged out successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
    }

    @PostMapping("/user_finding_email")
    public ResponseEntity<String> resetPasswordRequest(@RequestParam String email) {
        String token = registerService.generatePasswordResetToken(email);
        if (token != null) {
            String resetLink = "http://localhost:3000/new_pass?token=" + token;
            mailService.sendResetPasswordEmail(email, resetLink);
            return ResponseEntity.ok("Password reset email sent.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @PostMapping("/reset_password")

    public ResponseEntity<String> resetPasswordRequest_reset(@RequestParam String email) {
        register user = registerRepo.findByEmail(email);
        if (user != null) {
            // Check if a token already exists for the user
            PasswordResetToken existingToken = tokenRepository.findByUser(user);
            if (existingToken != null && !existingToken.isExpired()) {
                // Update the existing token
                existingToken.setToken(UUID.randomUUID().toString());
                existingToken.setExpiryDate(LocalDateTime.now().plusMinutes(2)); // Update expiry date
                tokenRepository.save(existingToken);
                return ResponseEntity.ok(existingToken.getToken());
            } else if (existingToken != null) {
                // Delete expired token
                tokenRepository.delete(existingToken);
            }

            // Create a new token
            String token = UUID.randomUUID().toString(); // Generate a unique token
            PasswordResetToken resetToken = new PasswordResetToken(token, user);
            tokenRepository.save(resetToken);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }


    //now reseting the password and creating the new passord using spring boot


    @PostMapping("/api/reset_password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        // Retrieve the token from the database
        PasswordResetToken resetToken = tokenRepository.findByToken(token);

        if (resetToken == null || resetToken.isExpired()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
        }

        // Get the associated user from the PasswordResetToken entity
        register user = resetToken.getUser();

        if (user != null) {
            // Encode the new password before saving it
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);

            // Optionally, reset the token to null after a successful password reset
            user.setToken(null);

            // Save the updated user information
            registerRepo.save(user);

            // Optionally, delete the password reset token after the password is successfully reset
            tokenRepository.delete(resetToken);

            return ResponseEntity.ok("Password has been reset successfully.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
    }






}
