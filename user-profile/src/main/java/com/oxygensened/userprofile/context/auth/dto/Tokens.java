package com.oxygensened.userprofile.context.auth.dto;

public record Tokens(String userId, String accessToken, String refreshToken) {
}
