package com.oxygensened.userprofile.context.notifications;

import com.oxygensened.userprofile.domain.User;
import org.springframework.stereotype.Service;

@Service
public class NotificationsService {

    private final NotificationsRepository notificationsRepository;

    public NotificationsService(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    public void sendEmailVerificationLink(User user, String token) {
        var recipient = new Mail.Recipient(user.email(), user.id().toString());
        var message = new Mail("Email verification",
                               "Please click the link below to verify your email address: http://localhost:8080/verify-email?token=" + token,
                               recipient);

        notificationsRepository.send(message);
    }

    public void sendPasswordResetLink(User user, String token) {
        var recipient = new Mail.Recipient(user.email(), user.id().toString());
        var message = new Mail("Password reset",
                               "Please click the link below to reset your password: http://localhost:8080/reset-password?token=" + token,
                               recipient);

        notificationsRepository.send(message);
    }
}
