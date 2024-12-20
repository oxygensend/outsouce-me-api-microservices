package com.oxygensened.userprofile.infrastructure.services.auth;

import com.oxygensened.userprofile.application.auth.AuthRepository;
import com.oxygensened.userprofile.application.auth.dto.CreateUserCommand;
import com.oxygensened.userprofile.application.auth.dto.RegisterUserCommand;
import com.oxygensened.userprofile.application.auth.dto.Tokens;
import com.oxygensened.userprofile.infrastructure.services.auth.dto.AuthRegisterRequest;
import com.oxygensened.userprofile.infrastructure.services.auth.dto.CreateUserRequest;
import com.oxygensened.userprofile.infrastructure.services.auth.dto.UserIdRequest;

final class AuthRestRepository implements AuthRepository {
    private final AuthClient authClient;

    AuthRestRepository(AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    public Tokens register(RegisterUserCommand command) {
        var request = AuthRegisterRequest.create(command);
        var response = authClient.register(request);
        return new Tokens(response.id(), response.accessToken(), response.refreshToken());
    }

    @Override
    public String generateEmailVerificationToken(String externalId) {
        var request = new UserIdRequest(externalId);
        return authClient.generateEmailVerificationToken(request).token();
    }

    @Override
    public String generatePasswordResetToken(String externalId) {
        var request = new UserIdRequest(externalId);
        return authClient.generatePasswordResetToken(request).token();
    }

    @Override
    public void createUser(CreateUserCommand command) {
        var request = CreateUserRequest.fromUser(command.user(), command.password());
        authClient.createUser(request);
    }
}
