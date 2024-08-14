package org.example.personal_websitebackend.service.registerService;

import org.example.personal_websitebackend.configuration.JwtUtil;
import org.example.personal_websitebackend.presentation.login;
import org.example.personal_websitebackend.presentation.userRegister.register;
import org.example.personal_websitebackend.repository.registerRepo.registerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
