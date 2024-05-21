package com.oxygensend.messenger.application.mail.dto;

import com.oxygensend.messenger.domain.User;

public record SendMailCommand(User from,
                              User to,
                              String subject,
                              String body) {
}
