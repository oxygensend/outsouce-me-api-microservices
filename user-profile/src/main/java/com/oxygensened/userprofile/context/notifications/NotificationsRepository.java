package com.oxygensened.userprofile.context.notifications;

public interface NotificationsRepository {
    void sendMail(Mail mail);

    void sendInternalMessage(InternalMessage internalMessage);

}
