package com.mail.service;

import com.mail.domain.EmailEntity;
import com.mail.enuns.MailState;
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
        Mockito.when(emailRepository.findAllByState(MailState.PENDING))
                .thenReturn(Collections.singletonList(emailSpy));
        emailService.sendAllEmail();
        Assert.assertNotNull(emailSpy.getSendDate());
    }

   @Test
    public void shouldRemoveEmailIfEmailsInvalid() {
        EmailEntity emailSpy = Mockito.spy(createEmail("emailinvalid.com"));
        Mockito.when(emailRepository.findAllByState(MailState.PENDING))
                .thenReturn(Collections.singletonList(emailSpy));
        emailService.sendAllEmail();
        Assert.assertNull(emailSpy.getSendDate());
    }

    private EmailEntity createEmail(String email) {
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setCode(1L);
        emailEntity.setAddressee(email);
        return emailEntity;
    }
}
