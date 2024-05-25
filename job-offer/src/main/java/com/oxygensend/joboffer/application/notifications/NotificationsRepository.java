package com.oxygensend.joboffer.application.notifications;

public interface NotificationsRepository {
    void sendMail(Mail mail);

    void sendInternalMessage(InternalMessage message);
}
