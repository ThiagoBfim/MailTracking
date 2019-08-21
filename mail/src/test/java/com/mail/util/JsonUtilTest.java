package com.mail.util;

import com.mail.domain.EmailEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonUtilTest {

    @Test
    public void formatObjectToJson() {
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setCodigo(1L);
        emailEntity.setEmailDestinatario("email@mail.com");
        String emailJson = JsonUtil.formatObjectToJson(emailEntity);
        assertEquals("{\"emailDestinatario\":\"email@mail.com\",\"situacao\":\"PENDENTE\",\"codigo\":1}",
                emailJson);
    }
}
