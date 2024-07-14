package com.oxygensend.joboffer.application.job_offer.dto.view;

import com.oxygensend.joboffer.domain.entity.part.Experience;
import java.util.List;

public record JobOfferOrderView(Long id, Experience experience, List<String> technologies, Double lon, Double lat) {
}
