package com.oxygensened.userprofile.infrastructure.services.notifications;

import com.oxygensened.userprofile.application.notifications.InternalMessage;
import com.oxygensened.userprofile.application.notifications.Mail;
import com.oxygensened.userprofile.application.notifications.NotificationEvent;
import com.oxygensened.userprofile.application.notifications.NotificationsRepository;
import com.oxygensened.userprofile.application.properties.UserProfileProperties;
import com.oxygensened.userprofile.infrastructure.services.ServiceProperties;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.slf4j.MDC;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
class NotificationsKafkaRepository implements NotificationsRepository {

    private final KafkaTemplate<String, NotificationEvent> notificationsKafkaTemplate;
    private final String login;
    private final String serviceId;

    NotificationsKafkaRepository(KafkaTemplate<String, NotificationEvent> notificationsKafkaTemplate,
                                 UserProfileProperties userProfileProperties,
                                 ServiceProperties serviceProperties) {
        this.notificationsKafkaTemplate = notificationsKafkaTemplate;
        this.serviceId = userProfileProperties.serviceId();
        this.login = serviceProperties.notifications().login();
    }

    @Override
    public void sendMail(Mail mail) {
        send(mail);
    }

    @Override
    public void sendInternalMessage(InternalMessage internalMessage) {
        send(internalMessage);
    }


    private void send(NotificationEvent message) {
        RecordHeaders headers = new RecordHeaders();
        headers.add("type", message.getClass().getSimpleName().getBytes());
        headers.add("serviceId", serviceId.getBytes());
        headers.add("login", login.getBytes());
        headers.add("requestId", Optional.ofNullable(MDC.get("RequestId")).map(String::getBytes).orElse(null));
        ProducerRecord<String, NotificationEvent> record =
            new ProducerRecord<>(notificationsKafkaTemplate.getDefaultTopic(),
                                 null, UUID.randomUUID().toString(), message, headers);
        notificationsKafkaTemplate.send(record);
    }
}
