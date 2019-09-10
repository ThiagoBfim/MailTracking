package com.mail.domain;

import com.mail.enuns.MailState;
import com.mail.util.JsonUtil;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_EMAIL")
public class EmailEntity extends BaseEntity {

    @Column(name = "DS_EMAIL_ADDRESSEE", nullable = false)
    private String addressee; //destinatario

    @Column(name = "DS_MESSAGE", nullable = false, length = 5000)
    private String message;

    @Column(name = "DS_SUBJECT", nullable = false)
    private String subject;

    @Column(name = "DT_SEND")
    private LocalDateTime sendDate;

    @Column(name = "DT_UPDATE")
    private LocalDateTime updateDate;

    @Column(name = "TP_SITUACAO")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PENDING'")
    private MailState state = MailState.PENDING;

    public String getAddressee() {
        return addressee;
    }

    public EmailEntity setAddressee(String addressee) {
        this.addressee = addressee;
        return this;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public EmailEntity setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
        return this;
    }

    public MailState getState() {
        return state;
    }

    public EmailEntity setState(MailState state) {
        this.state = state;
        return this;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public EmailEntity setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public String toJson() {
        return JsonUtil.formatObjectToJson(this);
    }

    public String getMessage() {
        return message;
    }

    public EmailEntity setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public EmailEntity setSubject(String subject) {
        this.subject = subject;
        return this;
    }
}
