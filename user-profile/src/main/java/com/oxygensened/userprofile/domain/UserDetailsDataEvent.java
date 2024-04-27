package com.oxygensened.userprofile.domain;

import com.oxygensened.userprofile.infrastructure.kafka.Topics;
import java.util.Map;

public record UserDetailsDataEvent(String id, Map<String, Object> fields) implements DomainEvent {

    public UserDetailsDataEvent(Long id, Map<String, Object> fields) {
        this(id.toString(), fields);
    }

    @Override
    public String key() {
        return id;
    }

    @Override
    public Topics topic() {
        return Topics.USER_DATA;
    }
}
