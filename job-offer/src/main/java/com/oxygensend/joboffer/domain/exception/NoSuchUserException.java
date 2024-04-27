package com.oxygensend.joboffer.domain.exception;

import com.oxygensend.commons_jdk.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSuchUserException extends ApiException {
    public NoSuchUserException(String message) {
        super(message);
    }

    public static NoSuchUserException withId(String id) {
        return new NoSuchUserException("No such user %s.".formatted(id));
    }
}
