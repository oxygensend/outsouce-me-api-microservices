package com.oxygensend.joboffer.context.job_offer.dto;

import com.oxygensend.joboffer.domain.entity.User;

public record UserView(String id,
                       String fullName,
                       String thumbnailPath,
                       String activeJobPosition) {


}
