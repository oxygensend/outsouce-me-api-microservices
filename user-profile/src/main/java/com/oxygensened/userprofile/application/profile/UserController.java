package com.oxygensened.userprofile.application.profile;

import com.oxygensend.commonspring.PagedListView;
import com.oxygensened.userprofile.application.cache.CacheData;
import com.oxygensened.userprofile.application.profile.dto.request.UserDetailsRequest;
import com.oxygensened.userprofile.application.profile.dto.view.DeveloperView;
import com.oxygensened.userprofile.application.profile.dto.view.UserSearchView;
import com.oxygensened.userprofile.application.profile.dto.view.UserView;
import com.oxygensened.userprofile.domain.entity.part.AccountType;
import com.oxygensened.userprofile.domain.entity.part.Experience;
import com.oxygensened.userprofile.domain.repository.filters.UserFilter;
import com.oxygensened.userprofile.domain.repository.filters.UserSort;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "User profile")
@CrossOrigin
@RequestMapping("/api/v1/users")
@RestController
class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @Cacheable(value = CacheData.USER_CACHE, key = CacheData.USER_KEY)
    @GetMapping("/{id}")
    public UserView show(@PathVariable Long id) {
        return userService.getUser(id);
    }

    //    @Cacheable(value = CacheData.THUMBNAIL_CACHE, key = CacheData.THUMBNAIL_KEY)
    @GetMapping(value = "/thumbnails/{filename:.+}", produces = "image/webp")
    @ResponseStatus(HttpStatus.OK)
    public Resource getImage(@PathVariable String filename) {
        return userService.loadThumbnail(filename);
    }

    //    @Cacheable(value = CacheData.THUMBNAIL_CACHE, key = CacheData.THUMBNAIL_BY_USER_ID_KEY)
    @GetMapping(value = "/{id}/thumbnail", produces = "image/webp")
    @ResponseStatus(HttpStatus.OK)
    public Resource getImage(@PathVariable Long id) {
        return userService.loadThumbnailByUserId(id);
    }

    @Cacheable(value = CacheData.USER_CACHE, key = CacheData.DEVELOPERS_KEY, unless = "#pageable.pageNumber > 20", cacheManager = "developersCacheManager")
    @GetMapping("/developers-offers")
    public PagedListView<DeveloperView> developersPaginatedList(@RequestParam(required = false) UserSort sort,
                                                                @RequestParam(name = "technologies", required = false)
                                                                List<String> technologies,
                                                                @RequestParam(name = "address.postCode", required = false)
                                                                String postCode,
                                                                @RequestParam(name = "address.city", required = false)
                                                                String city,
                                                                @RequestParam(name = "experience", required = false)
                                                                Experience experience,
                                                                Pageable pageable) {
        var filters = UserFilter.builder()
                                .sort(sort)
                                .technologies(technologies)
                                .postCode(postCode)
                                .city(city)
                                .experience(experience)
                                .lookingForJob(true)
                                .accountType(AccountType.DEVELOPER)
                                .build();

        return userService.getPaginatedDevelopers(filters, pageable);
    }

    @PatchMapping("/{id}")
    UserView update(@PathVariable @Parameter() Long id, @RequestBody UserDetailsRequest request) {
        return userService.updateUserDetails(id, request);
    }

    @GetMapping
    PagedListView<UserView> paginatedList(@RequestParam(required = false) AccountType accountType,
                                          @RequestParam(required = false) Boolean lookingForJob,
                                          @RequestParam(required = false) UserSort order,
                                          Pageable pageable) {
        var filters = UserFilter.builder()
                                .accountType(accountType)
                                .lookingForJob(lookingForJob)
                                .sort(order)
                                .build();

        var page = userService.getPaginatedUsers(filters, pageable);
        return new PagedListView<>(page.getContent(), (int) page.getTotalElements(), page.getNumber() + 1,
                                   page.getTotalPages());
    }

    @PostMapping(value = "/{id}/upload-thumbnail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void uploadPhoto(@PathVariable Long id, @RequestBody MultipartFile file) {
        userService.uploadThumbnail(id, file);
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    PagedListView<UserSearchView> search(@RequestParam String query, Pageable pageable) {
        return userService.search(query, pageable);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{id}/add-redirect")
    void addRedirect(@PathVariable Long id) {
        userService.addRedirect(id);
    }
}
