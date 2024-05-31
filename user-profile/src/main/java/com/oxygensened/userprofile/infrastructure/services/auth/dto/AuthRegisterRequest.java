package com.oxygensened.userprofile.infrastructure.services.auth.dto;

import com.oxygensened.userprofile.application.auth.dto.RegisterUserCommand;
import java.util.Set;

public record AuthRegisterRequest(String identity,
                                  String password,
                                  Set<String> roles,
                                  String businessId) {


    public static AuthRegisterRequest create(RegisterUserCommand command) {
        return new AuthRegisterRequest(command.email(), command.password(), Set.of(command.role()), command.businessId());
    }
}