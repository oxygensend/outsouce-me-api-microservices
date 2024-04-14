package com.oxygensend.joboffer.context.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/job-offers/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    UserView createUser(CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }
}
