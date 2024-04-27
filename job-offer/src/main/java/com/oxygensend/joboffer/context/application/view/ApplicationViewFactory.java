package com.oxygensend.joboffer.context.application.view;

import com.oxygensend.joboffer.context.job_offer.view.JobOfferViewFactory;
import com.oxygensend.joboffer.context.user.view.UserViewFactory;
import com.oxygensend.joboffer.domain.entity.Application;
import com.oxygensend.joboffer.domain.entity.Attachment;
import java.util.List;
import org.springframework.stereotype.Component;

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
        return new ApplicationView(application.status(),
                                   application.description(),
                                   jobOfferView,
                                   userView,
                                   attachments);
    }

    public ApplicationListView createListView(Application application){
        var jobOfferView = jobOfferViewFactory.createBaseJobOfferView(application.jobOffer());
        return new ApplicationListView(application.id(),
                                       application.status(),
                                       jobOfferView,
                                       application.createdAt());
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
