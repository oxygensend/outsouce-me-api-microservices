package com.oxygensened.userprofile.application.auth.dto;

import com.oxygensened.userprofile.domain.entity.User;

public record CreateUserCommand(User user,
                                String password) {
}
