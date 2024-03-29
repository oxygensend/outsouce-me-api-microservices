package com.oxygensened.userprofile.context.technology.dto;

import jakarta.validation.constraints.NotEmpty;

public record TechnologyRequest(@NotEmpty String name) {
}
