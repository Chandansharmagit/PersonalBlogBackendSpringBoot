package org.example.personal_websitebackend.Controller;


import org.example.personal_websitebackend.configuration.JwtUtil;
import org.example.personal_websitebackend.presentation.chandanPortfolio;
import org.example.personal_websitebackend.presentation.forgotpass;
import org.example.personal_websitebackend.presentation.login;
import org.example.personal_websitebackend.presentation.mailsender;
import org.example.personal_websitebackend.presentation.userRegister.register;
import org.example.personal_websitebackend.service.chandanportData;
import org.example.personal_websitebackend.service.mailService;
import org.example.personal_websitebackend.service.registerService.registerService;
import org.example.personal_websitebackend.service.registerService.registerdata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class controller {






    @GetMapping("/")
    public String hello(){
        return "hello world";
    }

    @GetMapping("/ch")
    public String ch(){
        return "hello world";
    }

    @Autowired
    chandanportData chandanportData;


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private mailService mailService;

    @PostMapping("/updating_data")
    public String updateData(@RequestBody chandanPortfolio chandanPortfolio){

        chandanPortfolio saved = chandanportData.saveChandanPortfolio(chandanPortfolio);
        mailsender mailsender = new mailsender();
        mailsender.setName(chandanPortfolio.getName());
        mailsender.setEmail(chandanPortfolio.getEmail());
        mailsender.setText(chandanPortfolio.getText());

        // Send email
        try {
            mailService.sendMail(mailsender);
            return "Data saved and email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Data saved, but failed to send email.";
        }

    }


    @PostMapping("/mail_sending")
    public String sendmail(@RequestBody mailsender mailsender){
        mailService.sendMail(mailsender);
        return "email sent";
    }


    //saving the login user




    //saving the register user
    @Autowired
    private registerdata registerdata;

    @PostMapping("/register")
    public register saving(@RequestBody register register){
        return registerdata.saving_user(register);

    }


    @Autowired
    private registerService registerService;

    @PostMapping("/finding")
    public ResponseEntity<String> userfind(@RequestBody login login) {
      String user =   registerService.find_user(login.getEmail(), login.getPassword());
        if ("user found".equals(user)) {
//            String jwt = jwtUtil.generateToken(login.getEmail());
            // User found, return success response
            return ResponseEntity.ok(user);
        } else if ("wrong password".equals(user)) {
            // Wrong password, return error response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(user);
        } else {
            // User not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
        }


    }


    //sending the email to ther user if the email matched to database for to reseting old password

    @PostMapping("/user_finding_email")
    public ResponseEntity<String> userfindingbyEmail(@RequestBody forgotpass forgotpass){
        String users = registerService.finding_userByEmail(forgotpass.getEmail());
        if ("user found".equals(users)){
            return ResponseEntity.ok(users);
        } else if ("wrong email".equals(users)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(users);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(users);
        }
    }

    @GetMapping("/public")
    public ResponseEntity<String>publicuser(){
        return ResponseEntity.ok("yes i am public user");
    }


//    @PreAuthorize("hasRole('NORMAL')")
    @GetMapping("/normal")
    public ResponseEntity<String>normaluser(){
        return ResponseEntity.ok("yes i am normal user");
    }

    @GetMapping("/admin")
    public ResponseEntity<String>adminuser(){
        return ResponseEntity.ok("yes i am admin user");
    }




}