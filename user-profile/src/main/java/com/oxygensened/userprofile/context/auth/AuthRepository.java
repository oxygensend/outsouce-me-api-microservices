package com.oxygensened.userprofile.context.auth;

import com.oxygensened.userprofile.context.auth.dto.Tokens;

public interface AuthRepository {
    Tokens register(RegisterUserCommand command);
    String generateEmailVerificationToken(String externalId);
    String generatePasswordResetToken(String externalId);
}
