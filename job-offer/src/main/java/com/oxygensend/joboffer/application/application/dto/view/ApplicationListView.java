package com.oxygensend.joboffer.application.application.dto.view;

import com.oxygensend.joboffer.application.job_offer.dto.view.BaseJobOfferView;
import com.oxygensend.joboffer.domain.entity.part.ApplicationStatus;
import java.time.LocalDateTime;

public record ApplicationListView(Long id,
                                  ApplicationStatus status,
                                  BaseJobOfferView jobOffer,
                                  LocalDateTime createdAt) {
}
