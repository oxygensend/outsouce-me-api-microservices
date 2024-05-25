package com.oxygensend.joboffer.application.user.dto;

import com.oxygensend.joboffer.domain.entity.part.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(@NotEmpty
                                String name,
                                @NotEmpty
                                String surname,
                                @NotEmpty
                                @Email
                                String email,
                                @NotNull
                                AccountType accountType,
                                String activeJobPositon,
                                String thumbnail) {
}
