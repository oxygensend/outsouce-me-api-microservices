package com.oxygensened.userprofile.context.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record EmailRequest(@NotEmpty @Email String email) {
}
