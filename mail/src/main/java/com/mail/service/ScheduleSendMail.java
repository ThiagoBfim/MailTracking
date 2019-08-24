package com.mail.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleSendMail {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleSendMail.class.getName());

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void sendMailSchedule() {
        LOGGER.info("Executando o envio dos e-mails");
        emailService.sendAllEmail();
    }
}
