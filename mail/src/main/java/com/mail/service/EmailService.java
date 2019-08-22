package com.mail.service;

import com.mail.domain.EmailEntity;
import com.mail.repository.EmailRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private MailSenderImpl mailSender;

    void sendAllEmail() {
        List<EmailEntity> emails = emailRepository.findAllByDataEnvioIsNull();
        emails.forEach(email -> {
            if (isValidEmailAddress(email.getEmailDestinatario())) {
                mailSender.sendMail(email);
                updateEmail(email);
            } else {
                removeEmail(email);
            }
        });
    }

    private static boolean isValidEmailAddress(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            return false;
        }
        return EmailValidator.getInstance().isValid(email);
    }

    @Transactional
    void removeEmail(EmailEntity email) {
        emailRepository.delete(email);
    }

    @Transactional
    void updateEmail(EmailEntity email) {
        email.setDataEnvio(LocalDateTime.now());
        emailRepository.save(email);
    }
}
