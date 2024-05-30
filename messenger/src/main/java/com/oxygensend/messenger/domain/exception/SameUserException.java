package com.oxygensend.messenger.domain.exception;

import com.oxygensend.commonspring.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SameUserException extends ApiException {
    public SameUserException(String message) {
        super(message);
    }
}
