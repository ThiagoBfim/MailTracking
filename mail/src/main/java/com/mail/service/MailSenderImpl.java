package com.mail.service;

import com.mail.domain.EmailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderImpl {

    @Autowired
    private JavaMailSender javaMailSender;


    @Autowired
    private Environment environment;


    public void sendMail(EmailEntity email) {
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
