package com.oxygensened.userprofile.domain.exception;

import com.oxygensend.commonspring.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class JobPositionNotFoundException extends ApiException {
    public JobPositionNotFoundException(String message) {
        super(message);
    }

    public static JobPositionNotFoundException withId(Long id) {
        return new JobPositionNotFoundException("Job position with id " + id + " not found");
    }
}
