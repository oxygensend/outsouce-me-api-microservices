package com.oxygensend.joboffer.context.application.dto.view;

import com.oxygensend.joboffer.context.job_offer.dto.view.BaseJobOfferView;
import java.time.LocalDateTime;
import java.util.List;

public record ApplicationInfoView(String description,
                                  BaseJobOfferView jobOffer,
                                  LocalDateTime createdAt,
                                  List<AttachmentView> attachments) {

}
