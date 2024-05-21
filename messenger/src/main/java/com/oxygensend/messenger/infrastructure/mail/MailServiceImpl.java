package com.oxygensend.messenger.infrastructure.mail;

import com.oxygensend.messenger.application.mail.MailService;
import com.oxygensend.messenger.application.mail.dto.SendMailCommand;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

final class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public boolean send(SendMailCommand command) {
        try {
            javaMailSender.send(createMessage(command));
        } catch (MailException exception) {
            return false;
        }
        return true;
    }

    private SimpleMailMessage createMessage(SendMailCommand command) {
        var simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(command.from().email());
        simpleMailMessage.setTo(command.to().email());
        simpleMailMessage.setSubject(command.subject());
        simpleMailMessage.setText(command.subject());
        return simpleMailMessage;
    }
}
