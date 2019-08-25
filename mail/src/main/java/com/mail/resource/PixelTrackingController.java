package com.mail.resource;

import com.mail.domain.EmailEntity;
import com.mail.enuns.SituacaoEmail;
import com.mail.repository.EmailRepository;
import com.mail.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class PixelTrackingController {

    @Autowired
    private EmailRepository emailRepository;

    @GetMapping("/read")
    public ResponseEntity<String> updateReaded(@RequestParam(value = "id") Long id) {
        return emailRepository.findById(id).map(email -> {
            email.setSituacao(SituacaoEmail.READ);
            email.setDataAtualizacao(LocalDateTime.now());
            EmailEntity emailSaved = emailRepository.save(email);
            return new ResponseEntity<>(emailSaved.toJson(), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(createNotFoundJsonMessage(id), HttpStatus.NO_CONTENT));
    }

    private String createNotFoundJsonMessage(@RequestParam("id") Long id) {
        return JsonUtil.formatObjectToJson("Not found e-mail with id: " + id);
    }
}
