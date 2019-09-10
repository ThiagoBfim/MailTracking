package com.mail.util;

import com.mail.domain.EmailEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonUtilTest {

    @Test
    public void formatObjectToJson() {
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setCode(1L);
        emailEntity.setAddressee("email@mail.com");
        String emailJson = JsonUtil.formatObjectToJson(emailEntity);
        assertEquals("{\"addressee\":\"email@mail.com\",\"state\":\"PENDING\",\"code\":1}",
                emailJson);
    }
}
