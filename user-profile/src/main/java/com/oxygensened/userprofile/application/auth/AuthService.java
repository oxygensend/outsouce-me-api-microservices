package com.oxygensened.userprofile.application.auth;

import com.oxygensened.userprofile.application.auth.dto.RegisterUserCommand;
import com.oxygensened.userprofile.application.auth.dto.Tokens;
import com.oxygensened.userprofile.application.auth.dto.request.RegisterRequest;
import com.oxygensened.userprofile.application.auth.dto.view.RegisterView;
import com.oxygensened.userprofile.application.notifications.NotificationsService;
import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.exception.UserAlreadyExistsException;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import com.oxygensened.userprofile.domain.service.UserIdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final NotificationsService notificationsService;
    private final UserIdGenerator userIdGenerator;

    public AuthService(AuthRepository authRepository, UserRepository userRepository, NotificationsService notificationsService, UserIdGenerator userIdGenerator) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.notificationsService = notificationsService;
        this.userIdGenerator = userIdGenerator;
    }

    @Transactional
    public RegisterView register(RegisterRequest request) {
        checkIfUserExists(request.email());

        var userId = userIdGenerator.generate();
        var command = new RegisterUserCommand(request.email(), request.password(), request.accountType().role(), userId);
        Tokens tokens = authRepository.register(command);
        saveUser(request, tokens.userId(), userId);

        return new RegisterView(request.email(), tokens.accessToken(), tokens.refreshToken());
    }

    public void resendEmailVerificationLink(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> UserNotFoundException.withEmail(email));
        String token = authRepository.generateEmailVerificationToken(user.externalId());

        notificationsService.sendEmailVerificationLink(email, user.id().toString(), token);
    }

    public void resendPasswordResetLink(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> UserNotFoundException.withEmail(email));
        String token = authRepository.generatePasswordResetToken(user.externalId());

        notificationsService.sendPasswordResetLink(email, user.id(), token);
    }

    private void checkIfUserExists(String email) {
        userRepository.findByEmail(email)
                      .ifPresent(user -> {
                          throw new UserAlreadyExistsException();
                      });
    }

    private void saveUser(RegisterRequest request, String externalId, long userId) {
        var user = User.builder()
                       .id(userId)
                       .name(request.name())
                       .surname(request.surname())
                       .email(request.email())
                       .accountType(request.accountType())
                       .externalId(externalId)
                       .build();

        userRepository.save(user);
        notificationsService.sendWelcomingMessages(user);
    }


}
