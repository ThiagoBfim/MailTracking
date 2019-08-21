package com.mail.resource;

import com.mail.domain.EmailEntity;
import com.mail.enuns.SituacaoEmail;
import com.mail.repository.EmailRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class PixelTrackingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailRepository emailRepository;

    @Test
    public void shouldEmailMarkAsRead() throws Exception {
        EmailEntity email = createEmail();
        EmailEntity spyEmail = Mockito.spy(email);
        Mockito.when(emailRepository.findById(1L)).thenReturn(Optional.ofNullable(spyEmail));
        Mockito.when(emailRepository.save(Mockito.any())).thenReturn(email);
        this.mockMvc.perform(get("/mail/read?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"emailDestinatario\":\"email@mail.com\",\"situacao\":\"PENDENTE\",\"codigo\":1}"));
        Assert.assertEquals(SituacaoEmail.LIDO, spyEmail.getSituacao());
    }

    private EmailEntity createEmail() {
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setCodigo(1L);
        emailEntity.setEmailDestinatario("email@mail.com");
        return emailEntity;
    }
}
