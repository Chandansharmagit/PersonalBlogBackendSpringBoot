package org.example.personal_websitebackend.service.registerService;

import org.example.personal_websitebackend.configuration.JwtUtil;
import org.example.personal_websitebackend.presentation.resetingPassorrd.PasswordResetToken;
import org.example.personal_websitebackend.presentation.userRegister.register;
import org.example.personal_websitebackend.repository.PasswordResetTokenRepository;
import org.example.personal_websitebackend.repository.registerRepo.registerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class registerService implements registerdata{

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private registerRepo registerRepo;
    @Override
    public register saving_user(register register) {
        return registerRepo.save(register);
    }

    @Override
    public List<register> findall() {
        return registerRepo.findAll();
    }




    public String find_user(String email, String password) {
        register register = registerRepo.findByEmail(email);
        // Check if the register object is null
        if (register == null) {
            // User not found
            return "user not found";
        }

        // Check if the provided password matches the stored password
        if (register.getPassword().equals(password)) {
            //storing the token if the user found sucess
//            String jwt = jwtUtil.generateToken(register.getEmail());
            return "user found";
        } else {
            return "wrong password";
        }
    }

    //sending the email to the user

    public String finding_userByEmail(String email){
        register register = registerRepo.findByEmail(email);
        if(register == null){
            return "user not found";
        }else{
            return "user found";
        }
    }


    //finding the user and fetching the userdetails


    public register getUserByEmail(String email) {
        return registerRepo.findByEmail(email);
    }
    
    //this will not t thow tin the erro in the consle in the dta analsos in the following of the

//
//    llet debig tthe cod ein the code base in the following og the parrel in the console of the daa


    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    public String generatePasswordResetToken(String email) {
        register user = registerRepo.findByEmail(email);
        if (user != null) {
            String token = UUID.randomUUID().toString(); // Generate a unique token
            PasswordResetToken resetToken = new PasswordResetToken(token, user);
            tokenRepository.save(resetToken);
            return token;
        }
        return null;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void updatePassword(register user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        registerRepo.save(user);
    }




}
