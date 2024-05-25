package com.oxygensend.joboffer.application.notifications;

import com.oxygensend.joboffer.application.properties.JobOffersProperties;
import com.oxygensend.joboffer.application.properties.NotificationsProperties;
import com.oxygensend.joboffer.domain.entity.Application;
import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.entity.User;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class NotificationsService {
    private final NotificationsRepository notificationsRepository;
    private final NotificationsProperties notificationsProperties;

    public NotificationsService(NotificationsRepository notificationsRepository, JobOffersProperties properties) {
        this.notificationsRepository = notificationsRepository;
        this.notificationsProperties = properties.notifications();
    }

    public void sendJobOfferApplicationNotifications(Application application) {
        User applyingPerson = application.user();
        JobOffer jobOffer = application.jobOffer();

        sendEmail(notificationsProperties.jobOfferApplicationEmail().subject().formatted(jobOffer.name()),
                  notificationsProperties.jobOfferApplicationEmail().body().formatted(applyingPerson.fullName(), jobOffer.name()),
                  jobOffer.user());

        sendInternalMessage(notificationsProperties.jobOfferApplicationInternalMessage().body().formatted(applyingPerson.fullName(), jobOffer.name()),
                            Set.of(new InternalMessage.Recipient(jobOffer.user().id())));
    }

    public void sendJobOfferExpiredNotifications(JobOffer jobOffer) {
        List<User> appliers = jobOffer.applications().stream()
                                      .filter(application -> !application.deleted())
                                      .map(Application::user)
                                      .toList();

        sendJobOfferExpiredNotificationsToPrinciple(jobOffer);
        sendJobOfferExpiredNotificationsToAppliers(jobOffer, appliers);
    }

    public void sendJobOfferExpiredNotificationsToAppliers(JobOffer jobOffer) {
        List<User> appliers = jobOffer.applications().stream()
                                      .filter(application -> !application.deleted())
                                      .map(Application::user)
                                      .toList();

        sendJobOfferExpiredNotificationsToAppliers(jobOffer, appliers);
    }


    private void sendJobOfferExpiredNotificationsToPrinciple(JobOffer jobOffer) {
        sendEmail(notificationsProperties.jobOfferExpiredEmailToPrincipal().subject().formatted(jobOffer.name()),
                  notificationsProperties.jobOfferExpiredEmailToPrincipal().body().formatted(jobOffer.name()),
                  jobOffer.user());

        sendInternalMessage(notificationsProperties.jobOfferExpiredInternalMessageToPrincipal().body().formatted(jobOffer.name()),
                            Set.of(new InternalMessage.Recipient(jobOffer.user().id())));
    }

    private void sendJobOfferExpiredNotificationsToAppliers(JobOffer jobOffer, List<User> appliers) {
        Set<InternalMessage.Recipient> internalRecipients = appliers.stream()
                                                                    .map(user -> new InternalMessage.Recipient(user.id()))
                                                                    .collect(Collectors.toSet());

        sendInternalMessage(notificationsProperties.jobOfferExpiredInternalMessageToAppliers().body().formatted(jobOffer.name()),
                            internalRecipients);

        Set<Mail.Recipient> emailRecipients = appliers.stream()
                                                      .map(user -> new Mail.Recipient(user.email(), user.id()))
                                                      .collect(Collectors.toSet());

        sendEmail(notificationsProperties.jobOfferExpiredEmailToAppliers().subject().formatted(jobOffer.name()),
                  notificationsProperties.jobOfferExpiredEmailToAppliers().body().formatted(jobOffer.name()),
                  emailRecipients);
    }

    private void sendInternalMessage(String messageBody, Set<InternalMessage.Recipient> recipients) {
        InternalMessage message = new InternalMessage(messageBody, recipients);
        notificationsRepository.sendInternalMessage(message);
    }

    private void sendEmail(String subject, String body, Set<Mail.Recipient> recipients) {
        Mail message = new Mail(subject, body, recipients);
        notificationsRepository.sendMail(message);
    }

    private void sendEmail(String subject, String body, User user) {
        Mail.Recipient recipient = new Mail.Recipient(user.email(), user.id());
        Mail message = new Mail(subject, body, Set.of(recipient));
        notificationsRepository.sendMail(message);
    }
}
