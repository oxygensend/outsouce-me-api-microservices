package com.oxygensend.joboffer.context.user.event;

import java.util.Map;

public record UserDetailsEvent(String id,
                               Map<String, Object> fields) {
}
