package com.oxygensened.userprofile.infrastructure.services.opinions;

public record UserOpinionsDetailsEvent(String id, double opinionsRate, int opinionsCount) {
}
