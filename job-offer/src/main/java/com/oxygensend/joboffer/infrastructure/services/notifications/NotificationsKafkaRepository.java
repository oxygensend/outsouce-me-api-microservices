package com.oxygensend.joboffer.infrastructure.services.notifications;


import com.oxygensend.commonspring.request_context.RequestContext;
import com.oxygensend.joboffer.application.properties.JobOffersProperties;
import com.oxygensend.joboffer.application.notifications.InternalMessage;
import com.oxygensend.joboffer.application.notifications.Mail;
import com.oxygensend.joboffer.application.notifications.NotificationEvent;
import com.oxygensend.joboffer.application.notifications.NotificationsRepository;
import com.oxygensend.joboffer.infrastructure.services.ServiceProperties;
import java.util.UUID;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
final class NotificationsKafkaRepository implements NotificationsRepository {
    private final KafkaTemplate<String, NotificationEvent> notificationsKafkaTemplate;
    private final String login;
    private final String serviceId;
    private final RequestContext requestContext;

    NotificationsKafkaRepository(KafkaTemplate<String, NotificationEvent> notificationsKafkaTemplate, JobOffersProperties jobOffersProperties,
                                 ServiceProperties serviceProperties, RequestContext requestContext) {
        this.notificationsKafkaTemplate = notificationsKafkaTemplate;
        this.serviceId = jobOffersProperties.serviceId();
        this.login = serviceProperties.notifications().login();
        this.requestContext = requestContext;
    }


    @Override
    public void sendMail(Mail mail) {
        send(mail);
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
        headers.add("requestId", requestContext.requestId().getBytes());
        ProducerRecord<String, NotificationEvent> record = new ProducerRecord<>(notificationsKafkaTemplate.getDefaultTopic(),
                                                                                null, UUID.randomUUID().toString(), message, headers);
        notificationsKafkaTemplate.send(record);
    }
}
