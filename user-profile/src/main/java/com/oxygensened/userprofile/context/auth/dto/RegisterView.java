package com.oxygensened.userprofile.context.auth.dto;

public record RegisterView(String email, String accessToken, String refreshToken) {
}
