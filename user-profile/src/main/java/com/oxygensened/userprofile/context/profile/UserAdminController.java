package com.oxygensened.userprofile.context.profile;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User profile - admin section")
@CrossOrigin
@RequestMapping("/api/v1/admin/users")
@RestController
public class UserAdminController {

    private final UserAdminService userAdminService;

    public UserAdminController(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/recalculate-developers-popularity-rate")
    public void recalculateDevelopersPopularityRate() {
        userAdminService.updateDevelopersPopularityRateAsync();
    }
}
