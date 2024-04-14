package com.oxygensened.userprofile.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.FIELD, ElementType.METHOD})
public @interface Slug {
    String[] source() default {"name"};
}
