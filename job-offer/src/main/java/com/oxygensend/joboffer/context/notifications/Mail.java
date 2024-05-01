package com.oxygensend.joboffer.context.notifications;

import java.util.Set;

public record Mail(String subject, String body, Set<Recipient> recipients) implements NotificationEvent {

    public Mail(String subject, String body, Recipient recipient) {
        this(subject, body, Set.of(recipient));
    }

    public record Recipient(String email, String id) {
    }
}
