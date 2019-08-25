package com.mail.exception;

public class MailServerException extends RuntimeException {

    public MailServerException(String exception){
        super(exception);
    }

}
