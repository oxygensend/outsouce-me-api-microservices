package com.oxygensened.userprofile.domain.event;

import com.oxygensened.userprofile.context.Topics;

public interface DomainEvent {
    String key();

    Topics topic();
}
