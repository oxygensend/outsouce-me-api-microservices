package com.oxygensend.joboffer.application.application.dto.view;

import com.oxygensend.joboffer.application.job_offer.dto.view.JobOfferWithUserView;
import com.oxygensend.joboffer.application.user.dto.view.BaseUserView;
import com.oxygensend.joboffer.domain.entity.part.ApplicationStatus;
import java.time.LocalDateTime;
import java.util.List;

public record ApplicationView(ApplicationStatus status,
                              String description,
                              JobOfferWithUserView jobOffer,
                              BaseUserView user,
                              List<AttachmentView> attachments,
                              LocalDateTime createdAt) {


}
