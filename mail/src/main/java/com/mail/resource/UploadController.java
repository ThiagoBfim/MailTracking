package com.mail.resource;

import com.mail.domain.EmailEntity;
import com.mail.enuns.SituacaoEmail;
import com.mail.repository.EmailRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class UploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class.getName());

    @Autowired
    private EmailRepository emailRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDataEmails(@RequestParam("file") MultipartFile file) throws Exception {
        if (file == null) {
            throw new RuntimeException("You must select the a file for uploading");
        }
        String contentType = file.getContentType();
        if (!"application/vnd.ms-excel".equals(contentType)) {
            return new ResponseEntity<>("Content type must be application/vnd.ms-excel.\nYou have send a : " + contentType, HttpStatus.BAD_REQUEST);
        }

        Path fileTmp = Files.createTempFile("upload", "tmp");
        FileCopyUtils.copy(file.getBytes(), fileTmp.toFile());
        AtomicInteger countRegistroSalvos = new AtomicInteger();
        try (
                Reader reader = Files.newBufferedReader(fileTmp)) {
            List<CSVEmail> emailList = new CsvToBeanBuilder(reader)
                    .withType(CSVEmail.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();
            emailList.stream()
                    .filter(CSVEmail::isValid)
                    .map(emailCsv -> {
                        EmailEntity email = new EmailEntity();
                        email.setSituacao(SituacaoEmail.PENDING);
                        email.setEmailDestinatario(emailCsv.getDestinatario());
                        email.setMessage(emailCsv.getMessage());
                        email.setAssunto(emailCsv.getAssunto());
                        return email;
                    })
                    .forEach(emailEntity -> saveEmail(countRegistroSalvos, emailEntity));
        } catch (Exception e) {
            String message = getMessage(e);
            LOGGER.error(message, e);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("Removing file: {}", fileTmp.toAbsolutePath().toString());
        LOGGER.info("Foram salvos {} registros", countRegistroSalvos);
        fileTmp.toFile().delete();
        return new ResponseEntity<>("Foram salvos " + countRegistroSalvos + " registros", HttpStatus.OK);
    }

    private String getMessage(Exception e) {
        if (e.getCause() != null && e.getCause() instanceof CsvException) {
            return e.getCause().getMessage();
        } else {
            return e.getMessage();
        }
    }

    private void saveEmail(AtomicInteger countRegistroSalvos, EmailEntity emailEntity) {
        emailRepository.save(emailEntity);
        countRegistroSalvos.getAndIncrement();
    }

}
