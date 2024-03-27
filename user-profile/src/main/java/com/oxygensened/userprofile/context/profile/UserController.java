package com.oxygensened.userprofile.context.profile;

import com.oxygensend.commons_jdk.PagedListView;
import com.oxygensened.userprofile.context.profile.dto.UserOrder;
import com.oxygensened.userprofile.context.profile.dto.request.UserDetailsRequest;
import com.oxygensened.userprofile.context.profile.dto.view.UserView;
import com.oxygensened.userprofile.domain.AccountType;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User profile")
@RequestMapping("/api/v1/users")
@RestController
class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    UserView show(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PatchMapping("/{id}")
    UserView update(@PathVariable Long id, @RequestBody UserDetailsRequest request) {
        return userService.updateUserDetails(id, request);
    }

    @GetMapping
    PagedListView<UserView> paginatedList(@RequestParam(required = false) AccountType accountType,
                                          @RequestParam(required = false) Boolean lookingForJob,
                                          @RequestParam(required = false) UserOrder order,
                                          Pageable pageable) {
        var filters = UserFilters.builder()
                                 .accountType(accountType)
                                 .lookingForJob(lookingForJob)
                                 .order(order)
                                 .build();

        var page = userService.getPaginatedUsers(filters, pageable);
        return new PagedListView<>(page.getContent(), page.getNumberOfElements(), page.getNumber(), page.getTotalPages());
    }


}
