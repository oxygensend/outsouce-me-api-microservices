package com.oxygensend.joboffer.application.application.dto.request;

public record CreateApplicationRequest(String userId,
                                       Long jobOfferId,
                                       String description) {
}
