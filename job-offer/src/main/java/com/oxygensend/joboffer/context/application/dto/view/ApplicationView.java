package com.oxygensend.joboffer.context.application.dto.view;

import com.oxygensend.joboffer.context.job_offer.dto.view.JobOfferWithUserView;
import com.oxygensend.joboffer.context.user.dto.view.BaseUserView;
import com.oxygensend.joboffer.domain.entity.part.ApplicationStatus;
import java.util.List;

public record ApplicationView(ApplicationStatus status,
                              String description,
                              JobOfferWithUserView jobOffer,
                              BaseUserView user,
                              List<AttachmentView> attachments) {


}
