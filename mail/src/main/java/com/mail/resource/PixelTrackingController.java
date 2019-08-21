package com.mail.resource;

import com.mail.enuns.SituacaoEmail;
import com.mail.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/mail")
public class PixelTrackingController {

    @Autowired
    private EmailRepository emailRepository;

    @GetMapping("/read")
    public void updateReaded(@PathVariable(value = "id") Long id) {
        emailRepository.findById(id).ifPresent(email -> {
            email.setSituacao(SituacaoEmail.LIDO);
            email.setDataAtualizacao(LocalDateTime.now());
            emailRepository.save(email);
        });
    }
}
