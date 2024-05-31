package com.oxygensened.userprofile.application.notifications;

public interface NotificationsRepository {
    void sendMail(Mail mail);

    void sendInternalMessage(InternalMessage internalMessage);

}
