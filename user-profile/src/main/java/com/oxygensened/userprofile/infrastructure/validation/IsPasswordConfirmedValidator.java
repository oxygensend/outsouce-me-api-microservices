package com.oxygensened.userprofile.infrastructure.validation;

import com.oxygensened.userprofile.application.auth.dto.request.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsPasswordConfirmedValidator implements ConstraintValidator<IsPasswordConfirmed, RegisterRequest> {
    @Override
    public boolean isValid(RegisterRequest request, ConstraintValidatorContext constraintValidatorContext) {
        return request.password().equals(request.passwordConfirmation());
    }
}
