package com.oxygensend.messenger.application.user;

import com.oxygensend.messenger.application.user.dto.CreateUserRequest;
import com.oxygensend.messenger.application.user.dto.view.UserView;
import com.oxygensend.messenger.domain.User;
import com.oxygensend.messenger.domain.UserRepository;
import com.oxygensend.messenger.domain.exception.UserAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserView createUser(CreateUserRequest request) {
        userRepository.findByEmail(request.email()).ifPresent(user -> {
            throw new UserAlreadyExistsException();
        });

        var id = generateId();
        var user = new User(id, request.name(), request.surname(), request.email());

        return UserView.from(userRepository.save(user));
    }

    private String generateId() {
        return "int-" + System.nanoTime();
    }
}
