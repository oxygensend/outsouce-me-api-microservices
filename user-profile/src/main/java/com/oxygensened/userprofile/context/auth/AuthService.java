package com.oxygensened.userprofile.context.auth;

import com.oxygensened.userprofile.context.auth.dto.RegisterRequest;
import com.oxygensened.userprofile.context.auth.dto.RegisterView;
import com.oxygensened.userprofile.context.auth.dto.Tokens;
import com.oxygensened.userprofile.context.notifications.NotificationsService;
import com.oxygensened.userprofile.domain.User;
import com.oxygensened.userprofile.domain.UserRepository;
import com.oxygensened.userprofile.domain.exception.UserAlreadyExistsException;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final NotificationsService notificationsService;

    public AuthService(AuthRepository authRepository, UserRepository userRepository, NotificationsService notificationsService) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.notificationsService = notificationsService;
    }


    public RegisterView register(RegisterRequest request) {
        checkIfUserExists(request.email());

        var command = new RegisterUserCommand(request.email(), request.password(), request.accountType().role());
        Tokens tokens = authRepository.register(command);
        saveUser(request, tokens.userId());

        return new RegisterView(request.email(), tokens.accessToken(), tokens.refreshToken());
    }

    private void checkIfUserExists(String email) {
        userRepository.findByEmail(email)
                      .ifPresent(user -> {
                          throw new UserAlreadyExistsException();
                      });
    }

    private void saveUser(RegisterRequest request, String externalId) {
        var user = User.builder()
                       .name(request.name())
                       .surname(request.surname())
                       .email(request.email())
                       .accountType(request.accountType())
                       .slug(request.name().toLowerCase() + "-" + request.surname().toLowerCase())
                       .externalId(externalId)
                       .build();

        userRepository.save(user);
    }

    public void resendEmailVerificationLink(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> UserNotFoundException.withEmail(email));
        String token = authRepository.generateEmailVerificationToken(user.externalId());

        notificationsService.sendEmailVerificationLink(user, token);
    }

    public void resendPasswordResetLink(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> UserNotFoundException.withEmail(email));
        String token = authRepository.generatePasswordResetToken(user.externalId());

        notificationsService.sendPasswordResetLink(user, token);
    }
}
