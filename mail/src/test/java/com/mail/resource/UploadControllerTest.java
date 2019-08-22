package com.mail.resource;

import com.mail.repository.EmailRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UploadControllerTest {

    @MockBean
    private EmailRepository emailRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void shouldUploadDataWithData() throws Exception {
        String content = "destinatario,message,assunto\n" +
                "test@mail.com,TEST MESSAGE,Testando e-mail";
        MockMultipartFile firstFile = new MockMultipartFile("file",
                "filename.csv",
                "text/csv", content.getBytes());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                .file(firstFile))
                .andExpect(status().is(200))
                .andExpect(content().string("Foram salvos 1 registros"));
    }

    @Test
    public void shouldUploadDataWithouData() throws Exception {
        String content = "destinatario,message,assunto\n";
        MockMultipartFile firstFile = new MockMultipartFile("file",
                "filename.csv",
                "text/csv", content.getBytes());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                .file(firstFile))
                .andExpect(status().is(200))
                .andExpect(content().string("Foram salvos 0 registros"));
    }

    @Test
    public void shouldReturnErroWhenColumnsWasWrong() throws Exception {
        String content = "destinatario2,message2,assunto2\n";
        MockMultipartFile firstFile = new MockMultipartFile("file",
                "filename.csv",
                "text/csv", content.getBytes());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                .file(firstFile))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Header is missing required fields [MESSAGE,DESTINATARIO,ASSUNTO]"));
    }

    @Test
    public void shouldReturnErroWhenUploadDataIncorrectType() throws Exception {
        String content = "destinatario,message,assunto\n" +
                "test@mail.com,TEST MESSAGE,Testando e-mail";
        MockMultipartFile firstFile = new MockMultipartFile("file",
                "filename.csv",
                "json", content.getBytes());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                .file(firstFile))
                .andExpect(status().is(400))
                .andExpect(content().string("Content type must be text/csv"));
    }

}
