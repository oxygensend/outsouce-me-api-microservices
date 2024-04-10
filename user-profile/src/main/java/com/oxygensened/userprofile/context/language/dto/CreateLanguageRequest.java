package com.oxygensened.userprofile.context.language.dto;

import jakarta.validation.constraints.NotEmpty;

public record CreateLanguageRequest(@NotEmpty
                                    String name,
                                    String description) {
}