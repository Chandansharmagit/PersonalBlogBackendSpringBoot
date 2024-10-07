package org.example.personal_websitebackend.service;

import org.example.personal_websitebackend.presentation.EmailSaving;

import org.example.personal_websitebackend.repository.saving_email;
import org.springframework.stereotype.Service;

@Service
public interface emailsaving_data {
    public EmailSaving emailsaving(EmailSaving email_saving);
}
