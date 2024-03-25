package com.oxygensened.userprofile.infrastructure.services.auth.dto;

public record AuthResponse(String id, String accessToken, String refreshToken) {
}
