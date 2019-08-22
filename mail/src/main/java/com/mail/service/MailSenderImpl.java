package com.mail.service;

import com.mail.domain.EmailEntity;
import com.mail.enuns.SituacaoEmail;
import com.mail.repository.EmailRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.List;

@Service
public class MailSenderImpl {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private Environment environment;

    public void sendAllEmail() {
        List<EmailEntity> emails = emailRepository.findAllBySituacao(SituacaoEmail.PENDENTE);
        emails.forEach(email -> {
            if (isValidEmailAddress(email.getEmailDestinatario())) {
                sendMail(email);
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

    private void sendMail(EmailEntity email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email.getEmailDestinatario());

        msg.setSubject(email.getAssunto());
        msg.setText(email.getMessage() + includePixelTracking());

        javaMailSender.send(msg);
    }

    private String includePixelTracking() {
        return "<img scr='" + environment.getProperty("system.domain-name") + "' " +
                "width=0; higth=0; style=\"position:absolute; visibility:hidden\" style=\"display:none\">";
    }
}
