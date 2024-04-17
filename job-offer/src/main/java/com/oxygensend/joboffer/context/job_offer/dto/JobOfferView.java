package com.oxygensend.joboffer.context.job_offer.dto;

public record JobOfferView(Long id,
                           String slug,
                           String name,
                           String description,
                           String shortDescription,
                           UserView user
) {
}
