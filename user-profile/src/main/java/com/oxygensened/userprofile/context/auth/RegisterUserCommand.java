package com.oxygensened.userprofile.context.auth;

public record RegisterUserCommand(String email, String password, String role) {
}
