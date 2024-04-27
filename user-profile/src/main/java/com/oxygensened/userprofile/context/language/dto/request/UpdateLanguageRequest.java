package com.oxygensened.userprofile.context.language.dto.request;

import org.openapitools.jackson.nullable.JsonNullable;

public record UpdateLanguageRequest(JsonNullable<String> name,
                                    JsonNullable<String> description) {
}
