package com.oxygensened.userprofile.infrastructure.services.joboffers;

import com.oxygensened.userprofile.domain.entity.part.Experience;
import java.util.Set;

public record JobOfferDataEvent(Long id, String userId, Experience experience, Set<String> technologies, Event event) {
}
