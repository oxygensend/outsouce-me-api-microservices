package com.oxygensend.messenger.application.mail;

import com.oxygensend.messenger.application.mail.dto.MailMessageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequestMapping("api/v1/mail-messages")
@RestController
class MailMessageEndpoint {

    private final MailMessageService mailMessageService;

    MailMessageEndpoint(MailMessageService mailMessageService) {
        this.mailMessageService = mailMessageService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    void sendMailMessage(@RequestBody @Validated MailMessageRequest request) {
        mailMessageService.sendMailMessage(request);
    }
}
