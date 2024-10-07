package org.example.personal_websitebackend.repository;

import org.example.personal_websitebackend.presentation.EmailSaving;

import org.springframework.data.jpa.repository.JpaRepository;

public interface saving_email extends JpaRepository<EmailSaving,Integer> {
}
