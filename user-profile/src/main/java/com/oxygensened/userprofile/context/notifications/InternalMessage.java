package com.oxygensened.userprofile.context.notifications;

import java.util.Set;

public record InternalMessage(String content, Set<Recipient> recipients) implements NotificationEvent {

    public InternalMessage(String content, Recipient recipient) {
        this(content, Set.of(recipient));
    }

    public record Recipient(String id) {

    }
}
