package com.oxygensened.userprofile.domain;

import com.oxygensened.userprofile.infrastructure.kafka.Topics;

public interface DomainEvent {
    String key();

    Topics topic();
}
