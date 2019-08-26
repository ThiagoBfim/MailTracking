package com.mail.service;

import com.mail.config.MailServiceProperties;
import com.mail.domain.EmailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@EnableConfigurationProperties(MailServiceProperties.class)
public class MailSenderImpl {

    @Autowired
    private JavaMailSender javaMailSender;

    private MailServiceProperties mailServiceProperties;

    public MailSenderImpl(MailServiceProperties properties) {
        this.mailServiceProperties = properties;
    }

    public void sendMail(EmailEntity email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(email.getMessage() + includePixelTracking(), true); // Use this or above line.
        helper.setTo(email.getAddressee());
        helper.setSubject(email.getSubject());
        javaMailSender.send(mimeMessage);
    }

    private String includePixelTracking() {
        return "<img scr='" + mailServiceProperties.getDomainName() + "' " +
                "width=0; higth=0; style=\"position:absolute; visibility:hidden\" style=\"display:none\">";
    }
}
