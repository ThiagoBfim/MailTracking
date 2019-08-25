package com.mail.service;

import com.mail.domain.EmailEntity;
import com.mail.repository.EmailRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @MockBean
    private EmailRepository emailRepository;

    @MockBean
    private MailSenderImpl mailSender;

    @Test
    public void shouldSendAllEmail() {
        EmailEntity emailSpy = Mockito.spy(createEmail("email@mail.com"));
        Mockito.when(emailRepository.findAllByDataEnvioIsNull())
                .thenReturn(Collections.singletonList(emailSpy));
        emailService.sendAllEmail();
        Assert.assertNotNull(emailSpy.getDataEnvio());
    }

   @Test
    public void shouldRemoveEmailIfEmailsInvalid() {
        EmailEntity emailSpy = Mockito.spy(createEmail("emailinvalid.com"));
        Mockito.when(emailRepository.findAllByDataEnvioIsNull())
                .thenReturn(Collections.singletonList(emailSpy));
        emailService.sendAllEmail();
        Assert.assertNull(emailSpy.getDataEnvio());
    }

    private EmailEntity createEmail(String email) {
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setCodigo(1L);
        emailEntity.setEmailDestinatario(email);
        return emailEntity;
    }
}
