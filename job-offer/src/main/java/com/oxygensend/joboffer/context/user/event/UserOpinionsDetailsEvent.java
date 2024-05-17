package com.oxygensend.joboffer.context.user.event;

public record UserOpinionsDetailsEvent(String id, double opinionsRate, int opinionsCount) {
}
