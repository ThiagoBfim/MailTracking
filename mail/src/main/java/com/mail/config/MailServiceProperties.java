package com.mail.config;

import com.mail.exception.MailServerException;
import org.apache.commons.lang3.StringUtils;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.reflections.ReflectionUtils.getAllMethods;

@ConfigurationProperties(prefix = MailServiceProperties.PREFIX_MAIL_SYSTEM_PROPERTIE)
public class MailServiceProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceProperties.class.getName());
    static final String PREFIX_MAIL_SYSTEM_PROPERTIE = "mail.system";
    private String domainName;

    @PostConstruct
    public void init() {
        validateRequiredProperties();
    }

    private void validateRequiredProperties() {
        getAllMethods(this.getClass(),
                ReflectionUtils.withModifier(Modifier.PUBLIC), ReflectionUtils.withPrefix("get"))
                .forEach(getter -> {
                    try {
                        getter.invoke(this);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                });
    }

    public String getDomainName() {
        if (StringUtils.isBlank(domainName)) {
            throw new MailServerException("Not found mail.system.domainName propertie");
        }
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
