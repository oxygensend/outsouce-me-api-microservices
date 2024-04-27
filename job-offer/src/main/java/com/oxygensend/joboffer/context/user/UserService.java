package com.oxygensend.joboffer.context.user;

import com.oxygensend.joboffer.context.user.view.UserDetailsView;
import com.oxygensend.joboffer.context.user.view.UserViewFactory;
import com.oxygensend.joboffer.domain.entity.User;
import com.oxygensend.joboffer.domain.exception.UserAlreadyExistsException;
import com.oxygensend.joboffer.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserViewFactory userViewFactory;

    public UserService(UserRepository userRepository, UserViewFactory userViewFactory) {
        this.userRepository = userRepository;
        this.userViewFactory = userViewFactory;
    }

    public UserDetailsView createUser(CreateUserRequest request) {
        userRepository.findByEmail(request.email()).ifPresent(user -> {
            throw new UserAlreadyExistsException();
        });

        var id = generateId();
        var user = new User(id,
                            request.name(),
                            request.surname(),
                            request.email(),
                            request.thumbnail(),
                            request.activeJobPositon(),
                            request.accountType());

        userRepository.save(user);
        return userViewFactory.createUserDetailsView(user);
    }

    private String generateId() {
        return "int-" + System.nanoTime();
    }
}
