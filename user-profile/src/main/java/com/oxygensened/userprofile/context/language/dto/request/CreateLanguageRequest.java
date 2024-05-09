package com.oxygensened.userprofile.context.language.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateLanguageRequest(@NotEmpty
                                    @Size(min = 3, max = 64, message = "Language length must be between 3 and 64 characters")
                                    String name,
                                    @Size(min = 3, max = 512, message = "Description length must be between 3 and 512 characters")
                                    String description) {
}
