package com.mail.resource;

import com.opencsv.bean.CsvBindByName;
import org.apache.commons.lang3.StringUtils;

public class CSVEmail {

    @CsvBindByName(column = "destinatario", required = true)
    private String destinatario;

    @CsvBindByName(column = "message", required = true)
    private String message;

    public String getDestinatario() {
        return destinatario;
    }

    public CSVEmail setDestinatario(String destinatario) {
        this.destinatario = destinatario;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CSVEmail setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isValid(){
        return StringUtils.isNotBlank(getDestinatario()) && StringUtils.isNotBlank(getMessage());
    }
}
