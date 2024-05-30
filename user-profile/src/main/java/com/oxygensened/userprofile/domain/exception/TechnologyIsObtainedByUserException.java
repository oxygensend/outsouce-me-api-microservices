package com.oxygensened.userprofile.domain.exception;

import com.oxygensend.commonspring.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TechnologyIsObtainedByUserException extends ApiException {
    public TechnologyIsObtainedByUserException(String message) {
        super(message);
    }
}
