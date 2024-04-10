package com.oxygensened.userprofile.domain;

import com.oxygensened.userprofile.config.properties.Topics;

public interface DomainEvent {
    String key();

    Topics topic();
}
