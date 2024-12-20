package com.oxygensend.joboffer.application.application.dto.view;

import com.oxygensend.joboffer.application.job_offer.dto.view.JobOfferViewFactory;
import com.oxygensend.joboffer.application.user.dto.view.UserViewFactory;
import com.oxygensend.joboffer.domain.entity.Application;
import com.oxygensend.joboffer.domain.entity.Attachment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationViewFactory {

    private final JobOfferViewFactory jobOfferViewFactory;
    private final UserViewFactory userViewFactory;


    public ApplicationViewFactory(JobOfferViewFactory jobOfferViewFactory, UserViewFactory userViewFactory) {
        this.jobOfferViewFactory = jobOfferViewFactory;
        this.userViewFactory = userViewFactory;
    }

    public ApplicationView create(Application application) {
        var jobOfferView = jobOfferViewFactory.createJobOfferWithUserView(application.jobOffer());
        var userView = userViewFactory.createUserView(application.user());
        var attachments = createAttachments(application);
        return new ApplicationView(application.id(),
                                   application.status(),
                                   application.description(),
                                   jobOfferView,
                                   userView,
                                   attachments,
                                   application.createdAt());
    }

    public ApplicationListView createListView(Application application) {
        var jobOfferView = jobOfferViewFactory.createBaseJobOfferView(application.jobOffer());
        return new ApplicationListView(application.id(),
                                       application.status(),
                                       jobOfferView,
                                       application.createdAt());
    }

    public ApplicationInfoView createInfoView(Application application) {
        var jobOfferView = jobOfferViewFactory.createBaseJobOfferView(application.jobOffer());
        var attachments = createAttachments(application);
        return new ApplicationInfoView(application.description(),
                                       jobOfferView,
                                       application.createdAt(),
                                       attachments);
    }


    private List<AttachmentView> createAttachments(Application application) {
        return application.attachments().stream()
                          .map(this::createAttachmentView)
                          .toList();

    }

    private AttachmentView createAttachmentView(Attachment attachment) {
        return new AttachmentView(attachment.id(), attachment.originalName());
    }

}
