package com.oxygensened.userprofile.application.auth.dto;

public record Tokens(String userId, String accessToken, String refreshToken) {
}
