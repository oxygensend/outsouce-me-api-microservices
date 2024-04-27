package com.oxygensend.joboffer.context.application.dto;

public record CreateApplicationRequest(String userId,
                                       Long jobOfferId,
                                       String description) {
}
