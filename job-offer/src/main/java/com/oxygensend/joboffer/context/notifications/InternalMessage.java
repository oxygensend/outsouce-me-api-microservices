package com.oxygensend.joboffer.context.notifications;

import java.util.Set;

public record InternalMessage(String message, Set<Recipient> recipients) {

    public InternalMessage(String message, Recipient recipient) {
        this(message, Set.of(recipient));
    }

    public record Recipient(String systemId) {

    }
}
