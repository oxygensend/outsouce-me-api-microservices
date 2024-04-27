package com.oxygensend.joboffer.context.application.dto.request;

import com.oxygensend.joboffer.domain.entity.part.ApplicationStatus;

public record ChangeStatusRequest(ApplicationStatus status) {
}
