package com.oxygensened.userprofile.context.auth.dto;

public record RegisterUserCommand(String email, String password, String role, String businessId) {

    public RegisterUserCommand(String email, String password, String role, Long businessId) {
        this(email, password, role, businessId.toString());
    }
}
