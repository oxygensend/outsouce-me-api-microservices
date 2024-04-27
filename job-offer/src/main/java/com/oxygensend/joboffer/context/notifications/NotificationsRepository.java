package com.oxygensend.joboffer.context.notifications;

public interface NotificationsRepository {
    void sendMail(Mail mail);

    void sendInternalMessage(InternalMessage message);
}
