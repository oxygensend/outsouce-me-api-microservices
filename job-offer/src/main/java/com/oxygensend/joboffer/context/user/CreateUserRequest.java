package com.oxygensend.joboffer.context.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record CreateUserRequest(@NotEmpty
                                String name,
                                @NotEmpty
                                String surname,
                                @NotEmpty
                                @Email
                                String email,
                                String activeJobPositon,
                                String thumbnail) {
}
