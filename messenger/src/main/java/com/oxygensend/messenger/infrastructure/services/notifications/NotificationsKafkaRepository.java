package com.oxygensend.messenger.infrastructure.services.notifications;


import com.oxygensend.messenger.application.notifications.InternalMessage;
import com.oxygensend.messenger.application.notifications.NotificationEvent;
import com.oxygensend.messenger.application.notifications.NotificationsRepository;
import com.oxygensend.messenger.application.properties.MessagesProperties;
import com.oxygensend.messenger.infrastructure.services.ServiceProperties;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.slf4j.MDC;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
final class NotificationsKafkaRepository implements NotificationsRepository {
    private final KafkaTemplate<String, NotificationEvent> notificationsKafkaTemplate;
    private final String login;
    private final String serviceId;

    NotificationsKafkaRepository(KafkaTemplate<String, NotificationEvent> notificationsKafkaTemplate,
                                 MessagesProperties messagesProperties,
                                 ServiceProperties serviceProperties) {
        this.notificationsKafkaTemplate = notificationsKafkaTemplate;
        this.serviceId = messagesProperties.serviceId();
        this.login = serviceProperties.notifications().login();
    }


    @Override
    public void sendInternalMessage(InternalMessage message) {
        send(message);
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
