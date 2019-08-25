package com.mail.domain;

import com.mail.enuns.SituacaoEmail;
import com.mail.util.JsonUtil;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_EMAIL")
public class EmailEntity extends BaseEntity {

    @Column(name = "DS_EMAIL_DESTINATARIO", nullable = false)
    private String emailDestinatario;

    @Column(name = "DS_MESSAGE", nullable = false)
    private String message;

    @Column(name = "DS_ASSUNTO", nullable = false)
    private String assunto;

    @Column(name = "DT_ENVIO")
    private LocalDateTime dataEnvio;

    @Column(name = "DT_ATUALIZACAO")
    private LocalDateTime dataAtualizacao;

    @Column(name = "TP_SITUACAO")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PENDING'")
    private SituacaoEmail situacao = SituacaoEmail.PENDING;

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public EmailEntity setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
        return this;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public EmailEntity setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
        return this;
    }

    public SituacaoEmail getSituacao() {
        return situacao;
    }

    public EmailEntity setSituacao(SituacaoEmail situacao) {
        this.situacao = situacao;
        return this;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public EmailEntity setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
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

    public String getAssunto() {
        return assunto;
    }

    public EmailEntity setAssunto(String assunto) {
        this.assunto = assunto;
        return this;
    }
}
