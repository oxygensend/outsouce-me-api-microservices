package com.oxygensened.userprofile.application.technology.dto;

import jakarta.validation.constraints.NotEmpty;

public record TechnologyRequest(@NotEmpty String name) {
}
