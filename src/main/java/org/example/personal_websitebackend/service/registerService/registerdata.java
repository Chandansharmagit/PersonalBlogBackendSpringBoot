package org.example.personal_websitebackend.service.registerService;

import org.example.personal_websitebackend.presentation.userRegister.register;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface registerdata {

    public register saving_user(register register);
    List<register> findall();


}
