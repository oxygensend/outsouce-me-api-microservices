package com.oxygensened.userprofile.domain.exception;

import com.oxygensend.commonspring.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSuchUniversityException extends ApiException {
    public NoSuchUniversityException(String message) {
        super(message);
    }

    public static NoSuchUniversityException withId(Long id) {
        return new NoSuchUniversityException("No such university with id: " + id);
    }
}
