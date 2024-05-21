package com.oxygensened.userprofile.context.notifications;

import com.oxygensened.userprofile.context.properties.NotificationsProperties;
import com.oxygensened.userprofile.context.properties.UserProfileProperties;
import com.oxygensened.userprofile.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
public class NotificationsService {

    private final NotificationsRepository notificationsRepository;
    private final UserProfileProperties userProfileProperties;
    private final NotificationsProperties notificationsProperties;

    public NotificationsService(NotificationsRepository notificationsRepository, UserProfileProperties userProfileProperties, UserProfileProperties properties) {
        this.notificationsRepository = notificationsRepository;
        this.userProfileProperties = userProfileProperties;
        this.notificationsProperties = properties.notifications();
    }

    public void sendEmailVerificationLink(String email, String userId, String token) {
        var recipient = new Mail.Recipient(email, userId);
        var message = new Mail(notificationsProperties.emailVerificationEmail().subject(),
                               notificationsProperties.emailVerificationEmail().body()
                                                      .formatted(userProfileProperties.emailVerificationFrontendUrl(), token), recipient);

        notificationsRepository.sendMail(message);
    }

    public void sendPasswordResetLink(String email, Long userId, String token) {
        var recipient = new Mail.Recipient(email, userId.toString());
        var message = new Mail(notificationsProperties.emailVerificationEmail().subject(),
                               notificationsProperties.passwordResetEmail().body()
                                                      .formatted(userProfileProperties.passwordResetFrontendUrl(), token), recipient);

        notificationsRepository.sendMail(message);
    }

    public void sendWelcomingMessages(User user) {
        sendWelcomingEmailMessage(user);
        sendWelcomingInternalMessage(user.id());
    }

    private void sendWelcomingEmailMessage(User user) {
        var recipient = new Mail.Recipient(user.email(), user.id().toString());
        var message = new Mail(notificationsProperties.welcomeMessageEmail().subject().formatted(user.name()),
                               notificationsProperties.welcomeMessageEmail().body(), recipient);

        notificationsRepository.sendMail(message);
    }

    private void sendWelcomingInternalMessage(Long userId) {
        var recipient = new InternalMessage.Recipient(userId.toString());
        var message = new InternalMessage(notificationsProperties.welcomeMessageInternal().body(), recipient);

        notificationsRepository.sendInternalMessage(message);
    }
}
