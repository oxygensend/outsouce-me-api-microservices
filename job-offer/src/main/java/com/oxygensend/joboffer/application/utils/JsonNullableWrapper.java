package com.oxygensend.joboffer.application.utils;

import org.openapitools.jackson.nullable.JsonNullable;

public class JsonNullableWrapper {

    private JsonNullableWrapper(){

    }
    public static  <T> JsonNullable<T> wrap(T entity) {
        return JsonNullable.of(entity);
    }

    public static  <T> T unwrap(JsonNullable<T> jsonNullable) {
        return jsonNullable == null ? null : jsonNullable.orElse(null);
    }

    public static  <T> boolean isPresent(JsonNullable<T> nullable) {
        return nullable != null && nullable.isPresent();
    }
}
