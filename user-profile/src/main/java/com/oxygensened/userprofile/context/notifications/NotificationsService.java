package com.oxygensened.userprofile.context.notifications;

import com.oxygensened.userprofile.context.UserProfileProperties;
import org.springframework.stereotype.Service;

@Service
public class NotificationsService {

    private final NotificationsRepository notificationsRepository;
    private final UserProfileProperties userProfileProperties;

    public NotificationsService(NotificationsRepository notificationsRepository, UserProfileProperties userProfileProperties) {
        this.notificationsRepository = notificationsRepository;
        this.userProfileProperties = userProfileProperties;
    }

    public void sendEmailVerificationLink(String email, String extUserId, String token) {
        var recipient = new Mail.Recipient(email, extUserId);
        var message = new Mail("Email verification",
                               "Please click the link below to verify your email address: %s?token=%s"
                                       .formatted(userProfileProperties.emailVerificationFrontendUrl(), token), recipient);

        notificationsRepository.sendMail(message);
    }

    public void sendPasswordResetLink(String email, String extUserId, String token) {
        var recipient = new Mail.Recipient(email, extUserId);
        var message = new Mail("Password reset",
                               "Please click the link below to reset your password: %s?token=%s"
                                       .formatted(userProfileProperties.passwordResetFrontendUrl(), token), recipient);

        notificationsRepository.sendMail(message);
    }
}
