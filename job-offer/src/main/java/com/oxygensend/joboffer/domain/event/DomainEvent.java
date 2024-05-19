package com.oxygensend.joboffer.domain.event;

public interface DomainEvent {
    String key();

    Topics topic();
}
