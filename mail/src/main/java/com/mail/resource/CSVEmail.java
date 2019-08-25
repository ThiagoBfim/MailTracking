package com.mail.resource;

import com.opencsv.bean.CsvBindByName;
import org.apache.commons.lang3.StringUtils;

public class CSVEmail {

    @CsvBindByName(column = "addressee", required = true)
    private String addressee; //destinatario

    @CsvBindByName(column = "message", required = true)
    private String message;

    @CsvBindByName(column = "subject", required = true)
    private String subject;

    public String getAddressee() {
        return addressee;
    }

    public CSVEmail setAddressee(String addressee) {
        this.addressee = addressee;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CSVEmail setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public CSVEmail setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    boolean isValid() {
        return StringUtils.isNotBlank(getAddressee()) && StringUtils.isNotBlank(getMessage()) && StringUtils.isNotBlank(getSubject());
    }
}
