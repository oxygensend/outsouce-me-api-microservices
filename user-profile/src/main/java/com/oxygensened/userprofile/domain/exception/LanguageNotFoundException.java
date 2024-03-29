package com.oxygensened.userprofile.domain.exception;

import com.oxygensend.commons_jdk.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LanguageNotFoundException extends ApiException {
    public LanguageNotFoundException(String message) {
        super(message);
    }

    public static LanguageNotFoundException withId(Long id) {
        return new LanguageNotFoundException("Language with id " + id + " not found");
    }
}
