package com.mail;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MailApplication {

    public static void main(String[] args) {
        String configLogationProperties = "spring.config.additional-location=classpath:prod.properties";
        new SpringApplicationBuilder(MailApplication.class)
                .properties(configLogationProperties)
                .build()
                .run(args);
    }


}
