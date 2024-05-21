package com.oxygensend.messenger.application.notifications;

import com.oxygensend.messenger.application.properties.MessagesProperties;
import com.oxygensend.messenger.application.properties.NotificationsProperties;
import com.oxygensend.messenger.domain.MailMessage;
import org.springframework.stereotype.Service;

@Service
public class NotificationsService {
    private final NotificationsRepository notificationsRepository;
    private final NotificationsProperties notificationsProperties;

    public NotificationsService(NotificationsRepository notificationsRepository, MessagesProperties messagesProperties) {
        this.notificationsRepository = notificationsRepository;
        this.notificationsProperties = messagesProperties.notifications();
    }


    public void sendInternalMessageInformingAboutMailMessageDelivery(MailMessage mailMessage) {
        var recipient = new InternalMessage.Recipient(mailMessage.recipient().id());
        var message = new InternalMessage(notificationsProperties.mailMessageDeliveryInternal().body()
                                                                 .formatted(mailMessage.sender().fullName()), recipient);
        notificationsRepository.sendInternalMessage(message);
    }


}
