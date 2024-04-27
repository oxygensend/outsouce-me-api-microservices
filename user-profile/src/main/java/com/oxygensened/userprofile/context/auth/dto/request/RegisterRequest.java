package com.oxygensened.userprofile.context.auth.dto.request;

import com.oxygensened.userprofile.domain.entity.part.AccountType;
import com.oxygensened.userprofile.infrastructure.validation.IsPasswordConfirmed;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@IsPasswordConfirmed
public record RegisterRequest(@NotEmpty
                              @Email
                              String email,
                              @Size(min = 2, message = "name have to be at least 2 characters")
                              @Size(max = 50, message = "Name have to be no longer than 50 characters")
                              String name,
                              @Size(min = 2, message = "Surname have to be at least 2 characters")
                              @Size(max = 50, message = "Surname have to be no longer than 50 characters")
                              String surname,
                              @NotNull
                              AccountType accountType,
                              @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                                      message = "Password must contain at least 8 characters, including at least one letter and one number")
                              String password,
                              @NotBlank
                              String passwordConfirmation) {
}
