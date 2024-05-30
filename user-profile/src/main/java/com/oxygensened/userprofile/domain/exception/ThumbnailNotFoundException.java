package com.oxygensened.userprofile.domain.exception;

import com.oxygensend.commonspring.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class ThumbnailNotFoundException extends ApiException {
    public ThumbnailNotFoundException() {
        super(null);
    }
}
