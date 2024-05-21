package com.oxygensend.messenger.application.mail.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record MailMessageRequest(@NotEmpty(message = "recipientId can't be empty") String recipientId,
                                 @NotEmpty(message = "senderId can't be empty") String senderId,
                                 @NotEmpty(message = "subject can't be empty")
                                 @Size(max = 255, message = "subject can't be longer than 255 characters")
                                 String subject,
                                 @NotEmpty(message = "body can't be empty")
                                 @Size(max = 2048, message = "body can't be longer than 2048 characters")
                                 String body) {

}
