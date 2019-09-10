package com.mail.repository;

import com.mail.domain.EmailEntity;
import com.mail.enuns.MailState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailRepository extends JpaRepository<EmailEntity, Long> {

    List<EmailEntity> findAllByState(MailState mailState);
}
