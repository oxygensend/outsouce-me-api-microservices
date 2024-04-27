package com.oxygensend.joboffer.context.application.dto.request;

public record CreateApplicationRequest(String userId,
                                       Long jobOfferId,
                                       String description) {
}
