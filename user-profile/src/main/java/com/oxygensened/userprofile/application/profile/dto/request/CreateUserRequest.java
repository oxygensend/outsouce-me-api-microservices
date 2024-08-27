package com.oxygensened.userprofile.application.profile.dto.request;

import com.oxygensened.userprofile.domain.entity.part.AccountType;

public record CreateUserRequest(String name,
                                String surname,
                                String email,
                                AccountType accountType,
                                String password,
                                String phoneNumber) {
}
