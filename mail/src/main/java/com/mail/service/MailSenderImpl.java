package com.mail.service;

import com.mail.domain.EmailEntity;
import com.mail.enuns.SituacaoEmail;
import com.mail.repository.EmailRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void sendAllEmail() {
        List<EmailEntity> emails = emailRepository.findAllBySituacao(SituacaoEmail.PENDENTE);
        emails.forEach(email -> {
            if (isValidEmailAddress(email.getEmailDestinatario())) {
                sendMail(email.getEmailDestinatario());
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

    private void sendMail(String destinatario) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(destinatario);

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);
    }
}
