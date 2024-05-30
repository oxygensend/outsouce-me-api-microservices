package com.oxygensend.joboffer.domain.exception;

import com.oxygensend.commonspring.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class OnlyDeveloperCanApplyForJobOfferException extends ApiException {
    public OnlyDeveloperCanApplyForJobOfferException() {
        super(null);
    }
}
