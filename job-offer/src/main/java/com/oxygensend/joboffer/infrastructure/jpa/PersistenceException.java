package com.oxygensend.joboffer.infrastructure.jpa;

public class PersistenceException extends RuntimeException {

    public PersistenceException(String message) {
        super(message);
    }
}
