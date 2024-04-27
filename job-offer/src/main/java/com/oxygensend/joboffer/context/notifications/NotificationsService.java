package com.oxygensend.joboffer.context.notifications;

import com.oxygensend.joboffer.domain.entity.Application;
import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
public class NotificationsService {
    private final NotificationsRepository notificationsRepository;

    public NotificationsService(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    public void sendJobOfferApplicationNotifications(Application application) {
        var applyingPerson = application.user();
        sendJobOfferApplicationEmail(applyingPerson, application.jobOffer());
        sendJobOfferApplicationInternalMessage(applyingPerson, application.jobOffer());
    }

    private void sendJobOfferApplicationInternalMessage(User applyingPerson, JobOffer jobOffer) {
        var recipient = new InternalMessage.Recipient(jobOffer.user().id());
        var message = new InternalMessage("User %s just applied for your offer %s.".formatted(applyingPerson.fullName(), jobOffer.name()), recipient);
        notificationsRepository.sendInternalMessage(message);
    }

    private void sendJobOfferApplicationEmail(User applyingPerson, JobOffer jobOffer) {
        var user = jobOffer.user();
        var recipient = new Mail.Recipient(user.email(), user.id());
        var message = new Mail("New application for your job offer",
                               "User %s just applied for your offer %s.".formatted(applyingPerson.fullName(), jobOffer.name()),
                               recipient);

        notificationsRepository.sendMail(message);
    }
}
