package com.oxygensend.joboffer.context.application.dto;

import com.oxygensend.joboffer.domain.ApplicationStatus;

public record ChangeStatusRequest(ApplicationStatus status) {
}
