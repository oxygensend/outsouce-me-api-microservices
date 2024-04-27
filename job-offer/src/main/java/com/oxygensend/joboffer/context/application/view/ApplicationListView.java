package com.oxygensend.joboffer.context.application.view;

import com.oxygensend.joboffer.context.job_offer.view.BaseJobOfferView;
import com.oxygensend.joboffer.domain.ApplicationStatus;
import java.time.LocalDateTime;

public record ApplicationListView(Long id,
                                  ApplicationStatus status,
                                  BaseJobOfferView jobOffer,
                                  LocalDateTime createdAt) {
}
