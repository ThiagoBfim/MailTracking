package com.mail.service;

import com.mail.config.MailServiceProperties;
import com.mail.domain.EmailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(MailServiceProperties.class)
public class MailSenderImpl {

    @Autowired
    private JavaMailSender javaMailSender;

    private MailServiceProperties mailServiceProperties;

    public MailSenderImpl(MailServiceProperties properties) {
        this.mailServiceProperties = properties;
    }

    public void sendMail(EmailEntity email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email.getAddressee());

        msg.setSubject(email.getSubject());
        msg.setText(email.getMessage() + includePixelTracking());

        javaMailSender.send(msg);
    }

    private String includePixelTracking() {
        return "<img scr='" + mailServiceProperties.getDomainName() + "' " +
                "width=0; higth=0; style=\"position:absolute; visibility:hidden\" style=\"display:none\">";
    }
}
