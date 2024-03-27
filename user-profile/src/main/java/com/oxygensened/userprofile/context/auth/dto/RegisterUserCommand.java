package com.oxygensened.userprofile.context.auth.dto;

public record RegisterUserCommand(String email, String password, String role) {
}
