package com.oxygensend.joboffer.application.application.dto.view;

import com.oxygensend.joboffer.application.job_offer.dto.view.BaseJobOfferView;
import java.time.LocalDateTime;
import java.util.List;

public record ApplicationInfoView(String description,
                                  BaseJobOfferView jobOffer,
                                  LocalDateTime createdAt,
                                  List<AttachmentView> attachments) {

}
