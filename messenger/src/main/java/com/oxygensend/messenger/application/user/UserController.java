package com.oxygensend.messenger.application.user;

import com.oxygensend.messenger.application.user.dto.CreateUserRequest;
import com.oxygensend.messenger.application.user.dto.view.UserView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/job-offers/users")
final class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    UserView createUser(CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }
}
