package com.oxygensened.userprofile.application.profile;


import com.oxygensened.userprofile.application.profile.dto.request.CreateUserRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/create-user")
    public void createUser(@RequestBody @Validated CreateUserRequest request){
        userAdminService.createUser(request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete-user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userAdminService.deleteUserById(id);
    }

}
