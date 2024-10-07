package org.example.personal_websitebackend.repository;

import org.example.personal_websitebackend.presentation.resetingPassorrd.PasswordResetToken;
import org.example.personal_websitebackend.presentation.userRegister.register;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByUser(register user);
    PasswordResetToken findByToken(String token);
}


