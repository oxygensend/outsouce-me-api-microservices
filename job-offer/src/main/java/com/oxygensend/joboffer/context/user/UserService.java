package com.oxygensend.joboffer.context.user;

import com.oxygensend.joboffer.domain.entity.User;
import com.oxygensend.joboffer.domain.exception.UserAlreadyExistsException;
import com.oxygensend.joboffer.domain.repository.UserRepository;
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
        var user = new User(
                id,
                request.name(),
                request.surname(),
                request.email(),
                request.thumbnail(),
                request.activeJobPositon()
        );

        userRepository.save(user);
        return UserView.from(user);
    }

    private String generateId() {
        return "int-" + System.nanoTime();
    }
}
