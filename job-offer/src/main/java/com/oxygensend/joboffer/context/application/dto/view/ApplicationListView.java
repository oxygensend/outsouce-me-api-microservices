package com.oxygensend.joboffer.context.application.dto.view;

import com.oxygensend.joboffer.context.job_offer.dto.view.BaseJobOfferView;
import com.oxygensend.joboffer.domain.entity.part.ApplicationStatus;
import java.time.LocalDateTime;

public record ApplicationListView(Long id,
                                  ApplicationStatus status,
                                  BaseJobOfferView jobOffer,
                                  LocalDateTime createdAt) {
}
