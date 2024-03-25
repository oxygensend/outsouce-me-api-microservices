package com.oxygensened.userprofile.infrastructure.services.notifications;

import com.oxygensened.userprofile.config.properties.ServiceProperties;
import com.oxygensened.userprofile.config.properties.UserProfileProperties;
import com.oxygensened.userprofile.context.notifications.Mail;
import com.oxygensened.userprofile.context.notifications.NotificationEvent;
import com.oxygensened.userprofile.context.notifications.NotificationsRepository;
import java.util.UUID;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

@Repository
class NotificationsKafkaRepository implements NotificationsRepository {

    private final KafkaTemplate<String, NotificationEvent<?>> notificationsKafkaTemplate;
    private final String login;
    private final String serviceId;

    NotificationsKafkaRepository(KafkaTemplate<String, NotificationEvent<?>> notificationsKafkaTemplate, UserProfileProperties userProfileProperties,
                                 ServiceProperties serviceProperties) {
        this.notificationsKafkaTemplate = notificationsKafkaTemplate;
        this.serviceId = userProfileProperties.serviceId();
        this.login = serviceProperties.notifications().login();
    }

    @Override
    public void send(Mail mail) {
        var event = new NotificationEvent<>(mail, login, serviceId);
        notificationsKafkaTemplate.sendDefault(UUID.randomUUID().toString(), event);
    }
}
