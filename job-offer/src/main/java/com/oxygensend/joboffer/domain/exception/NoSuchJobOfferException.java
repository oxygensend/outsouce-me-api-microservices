package com.oxygensend.joboffer.domain.exception;

import com.oxygensend.commonspring.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSuchJobOfferException extends ApiException {
    public NoSuchJobOfferException(String message) {
        super(message);
    }

    public static NoSuchJobOfferException withId(Long id) {
        return new NoSuchJobOfferException("No such job offer %d".formatted(id));
    }
}
