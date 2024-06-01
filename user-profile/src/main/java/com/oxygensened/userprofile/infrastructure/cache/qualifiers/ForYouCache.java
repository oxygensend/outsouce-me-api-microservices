package com.oxygensened.userprofile.infrastructure.cache.qualifiers;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.beans.factory.annotation.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Qualifier("forYouCache")
@Documented
@Retention(value = RUNTIME)
@Target( {ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface ForYouCache {
}
