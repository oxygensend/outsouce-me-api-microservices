package com.oxygensened.userprofile.domain.exception;

import com.oxygensend.commons_jdk.exception.ApiException;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends ApiException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException withId(UUID id) {
        return new UserNotFoundException("User with id " + id + " not found");
    }

    public static UserNotFoundException withEmail(String email) {
        return new UserNotFoundException("User with email " + email + " not found");
    }

    public static UserNotFoundException withExternalId(String externalId) {
        return new UserNotFoundException("User with external id " + externalId + " not found");
    }
}
