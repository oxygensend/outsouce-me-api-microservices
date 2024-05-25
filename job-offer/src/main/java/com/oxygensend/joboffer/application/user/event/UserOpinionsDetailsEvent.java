package com.oxygensend.joboffer.application.user.event;

public record UserOpinionsDetailsEvent(String id, double opinionsRate, int opinionsCount) {
}
