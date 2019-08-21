package com.mail.resource;

import com.mail.MailApplicationTests;
import com.mail.domain.EmailEntity;
import com.mail.enuns.SituacaoEmail;
import com.mail.repository.EmailRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PixelTrackingControllerTest extends MailApplicationTests {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private PixelTrackingController pixelTrackingController;

    @Test
    public void shouldEmailMarkAsRead() {
        createEmail();
        pixelTrackingController.updateReaded(1L);

        EmailEntity email = emailRepository.findById(1L).get();
        Assert.assertEquals(SituacaoEmail.LIDO, email.getSituacao());
    }

    private void createEmail() {
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setCodigo(1L);
        emailEntity.setEmailDestinatario("email@mail.com");
        emailRepository.save(emailEntity);
    }
}
