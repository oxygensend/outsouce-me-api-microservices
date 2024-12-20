package com.oxygensend.joboffer.application.utils;

import java.util.function.Consumer;
import org.openapitools.jackson.nullable.JsonNullable;

public class Patch {
    private Patch() {
    }

    public static  <T> void updateIfPresent(T object, Consumer<T> setter) {
        if (object != null) {
            setter.accept(object);
        }
    }

    public static  <T> void updateIfPresent(JsonNullable<T> nullable, Consumer<T> setter) {
        if (JsonNullableWrapper.isPresent(nullable)) {
            setter.accept(JsonNullableWrapper.unwrap(nullable));
        }
    }
}
