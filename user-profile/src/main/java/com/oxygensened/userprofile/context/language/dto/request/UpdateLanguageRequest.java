package com.oxygensened.userprofile.context.language.dto.request;

import jakarta.validation.constraints.Size;
import org.openapitools.jackson.nullable.JsonNullable;

public record UpdateLanguageRequest(
        @Size(min = 3, max = 64, message = "Language length must be between 3 and 64 characters")
        JsonNullable<String> name,
        @Size(min = 3, max = 512, message = "Description length must be between 3 and 64 characters")
        JsonNullable<String> description) {
}
