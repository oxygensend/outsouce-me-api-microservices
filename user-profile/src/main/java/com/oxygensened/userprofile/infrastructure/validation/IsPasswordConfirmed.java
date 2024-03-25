package com.oxygensened.userprofile.infrastructure.validation;

import jakarta.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsPasswordConfirmedValidator.class)
@Target( {ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface IsPasswordConfirmed {

    String message() default "The password and password confirmation fields are not equal.";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

}
