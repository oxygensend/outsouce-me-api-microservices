package com.oxygensend.joboffer.domain.exception;

import com.oxygensend.commons_jdk.exception.ApiException;

public class ApplicationAlreadyExistsException extends ApiException {
    public ApplicationAlreadyExistsException() {
        super("User already applied for this job offer.");
    }
}
