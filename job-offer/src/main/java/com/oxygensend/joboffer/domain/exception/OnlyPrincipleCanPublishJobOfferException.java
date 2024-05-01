package com.oxygensend.joboffer.domain.exception;

import com.oxygensend.commons_jdk.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class OnlyPrincipleCanPublishJobOfferException extends ApiException {
    public OnlyPrincipleCanPublishJobOfferException() {
        super(null);
    }
}