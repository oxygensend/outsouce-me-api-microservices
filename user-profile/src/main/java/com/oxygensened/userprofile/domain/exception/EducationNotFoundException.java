package com.oxygensened.userprofile.domain.exception;

import com.oxygensend.commonspring.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EducationNotFoundException extends ApiException {
    public EducationNotFoundException(String message) {
        super(message);
    }

    public static EducationNotFoundException withId(Long id) {
        return new EducationNotFoundException("Education with id " + id + " not found");
    }
}
