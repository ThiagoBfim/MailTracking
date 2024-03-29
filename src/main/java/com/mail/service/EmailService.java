package com.mail.service;

import com.mail.domain.EmailEntity;
import com.mail.enuns.MailState;
import com.mail.repository.EmailRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class.getName());

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private MailSenderImpl mailSender;

    void sendAllEmail() {
        final List<EmailEntity> emails = emailRepository.findAllByState(MailState.PENDING);
        AtomicInteger countErrorsMail = new AtomicInteger();
        emails.forEach(email -> {
            if (isValidEmailAddress(email.getAddressee())) {
                try {
                    mailSender.sendMail(email);
                    updateEmail(email);
                } catch (MessagingException e) {
                    LOGGER.error(e.getMessage(), e);
                    countErrorsMail.incrementAndGet();
                }
            } else {
                removeEmail(email);
            }
        });
        LOGGER.info("Total sending e-mails: {}", emails.size() - countErrorsMail.get());
        LOGGER.info("Total errors sending e-mails: {}", countErrorsMail);
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
        email.setSendDate(LocalDateTime.now());
        email.setState(MailState.SENT);
        emailRepository.save(email);
    }
}
