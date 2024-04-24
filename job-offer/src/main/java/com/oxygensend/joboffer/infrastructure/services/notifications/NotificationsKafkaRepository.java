package com.oxygensend.joboffer.infrastructure.services.notifications;


import com.oxygensend.joboffer.config.properties.JobOffersProperties;
import com.oxygensend.joboffer.config.properties.ServiceProperties;
import com.oxygensend.joboffer.context.notifications.Mail;
import com.oxygensend.joboffer.context.notifications.NotificationEvent;
import com.oxygensend.joboffer.context.notifications.NotificationsRepository;
import java.util.UUID;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
final class NotificationsKafkaRepository implements NotificationsRepository {
    private final KafkaTemplate<String, NotificationEvent<?>> notificationsKafkaTemplate;
    private final String login;
    private final String serviceId;

    NotificationsKafkaRepository(KafkaTemplate<String, NotificationEvent<?>> notificationsKafkaTemplate, JobOffersProperties jobOffersProperties,
                                 ServiceProperties serviceProperties) {
        this.notificationsKafkaTemplate = notificationsKafkaTemplate;
        this.serviceId = jobOffersProperties.serviceId();
        this.login = serviceProperties.notifications().login();
    }

    @Override
    public void send(Mail mail) {
        var event = new NotificationEvent<>(mail, login, serviceId);
        notificationsKafkaTemplate.sendDefault(UUID.randomUUID().toString(), event);
    }
}