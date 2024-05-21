package com.oxygensend.messenger.application.notifications;

public interface NotificationsRepository {

    void sendInternalMessage(InternalMessage message);
}
