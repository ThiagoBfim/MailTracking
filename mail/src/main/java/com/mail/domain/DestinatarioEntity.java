package com.mail.domain;

import com.mail.enuns.EstadoEmail;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_DESTINATARIO")
public class DestinatarioEntity extends BaseEntity {

    @Column(name = "DS_EMAIL", nullable = false)
    private String email;

    @Column(name = "DT_ENVIO")
    private LocalDateTime dataEnvio;

    @Column(name = "TP_SITUACAO")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PENDENTE'")
    private EstadoEmail situacao = EstadoEmail.PENDENTE;

    public String getEmail() {
        return email;
    }

    public DestinatarioEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public DestinatarioEntity setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
        return this;
    }

    public EstadoEmail getSituacao() {
        return situacao;
    }

    public DestinatarioEntity setSituacao(EstadoEmail situacao) {
        this.situacao = situacao;
        return this;
    }
}
