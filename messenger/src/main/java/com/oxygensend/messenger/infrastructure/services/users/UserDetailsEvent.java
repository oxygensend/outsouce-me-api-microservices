package com.oxygensend.messenger.infrastructure.services.users;

import java.util.Map;

public record UserDetailsEvent(String id,
                               Map<String, Object> fields) {
}
