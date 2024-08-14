package org.example.personal_websitebackend.repository;

import org.example.personal_websitebackend.presentation.mailsender;
import org.hibernate.query.criteria.JpaRoot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface mailRepo extends JpaRoot<mailsender> {
}
