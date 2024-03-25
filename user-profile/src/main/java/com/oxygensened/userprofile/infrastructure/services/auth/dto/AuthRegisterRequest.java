package com.oxygensened.userprofile.infrastructure.services.auth.dto;

import com.oxygensened.userprofile.context.auth.RegisterUserCommand;
import java.util.Set;

public record AuthRegisterRequest(String identity,
                                  String password,
                                  Set<String> roles) {


    public static AuthRegisterRequest create(RegisterUserCommand command) {
        return new AuthRegisterRequest(command.email(), command.password(), Set.of(command.role()));
    }
}