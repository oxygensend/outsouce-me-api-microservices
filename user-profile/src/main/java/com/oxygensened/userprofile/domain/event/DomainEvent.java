package com.oxygensened.userprofile.domain.event;

public interface DomainEvent {
    String key();

    Topics topic();
}
