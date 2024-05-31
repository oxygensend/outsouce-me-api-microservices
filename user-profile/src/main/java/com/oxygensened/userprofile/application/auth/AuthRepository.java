package com.oxygensened.userprofile.application.auth;

import com.oxygensened.userprofile.application.auth.dto.RegisterUserCommand;
import com.oxygensened.userprofile.application.auth.dto.Tokens;

public interface AuthRepository {
    Tokens register(RegisterUserCommand command);
    String generateEmailVerificationToken(String externalId);
    String generatePasswordResetToken(String externalId);

}
