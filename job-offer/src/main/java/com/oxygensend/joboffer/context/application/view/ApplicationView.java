package com.oxygensend.joboffer.context.application.view;

import com.oxygensend.joboffer.context.job_offer.view.JobOfferWithUserView;
import com.oxygensend.joboffer.context.user.view.BaseUserView;
import com.oxygensend.joboffer.domain.ApplicationStatus;
import java.util.List;

public record ApplicationView(ApplicationStatus status,
                              String description,
                              JobOfferWithUserView jobOffer,
                              BaseUserView user,
                              List<AttachmentView> attachments) {


}
