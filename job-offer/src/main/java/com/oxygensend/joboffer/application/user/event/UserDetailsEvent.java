package com.oxygensend.joboffer.application.user.event;

import java.util.Map;

public record UserDetailsEvent(String id,
                               Map<String, Object> fields) {
}
