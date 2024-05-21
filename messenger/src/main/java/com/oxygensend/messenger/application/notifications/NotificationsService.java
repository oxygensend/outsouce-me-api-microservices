package com.oxygensend.messenger.application.notifications;

import com.oxygensend.messenger.domain.MailMessage;
import org.springframework.stereotype.Service;

@Service
public class NotificationsService {
    private final NotificationsRepository notificationsRepository;

    public NotificationsService(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }


    public void sendInternalMessageInformingAboutMailMessageDelivery(MailMessage mailMessage) {
        var recipient = new InternalMessage.Recipient(mailMessage.recipient().id());
        var message = new InternalMessage("You have new mail message from user %s.".formatted(mailMessage.sender().fullName()), recipient);
        notificationsRepository.sendInternalMessage(message);
    }


}
