package org.example.personal_websitebackend.service;

import org.example.personal_websitebackend.presentation.EmailSaving;

import org.example.personal_websitebackend.repository.saving_email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class emailsaving_service implements emailsaving_data{
    @Autowired
    private saving_email saving_email;


    @Override
    public EmailSaving emailsaving(EmailSaving email_saving) {
        return saving_email.save(email_saving);
    }
}
