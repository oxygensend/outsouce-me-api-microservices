package com.oxygensend.messenger.application.mail;

import com.oxygensend.messenger.application.mail.dto.SendMailCommand;

public interface MailService {
    boolean send(SendMailCommand command);
}

