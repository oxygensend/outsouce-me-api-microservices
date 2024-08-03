package com.oxygensened.userprofile.application.profile;

import static com.oxygensened.userprofile.application.utils.Patch.updateIfPresent;

import com.oxygensend.commonspring.PagedListView;
import com.oxygensend.commonspring.exception.AccessDeniedException;
import com.oxygensend.commonspring.exception.UnauthorizedException;
import com.oxygensend.commonspring.request_context.RequestContext;
import com.oxygensened.userprofile.application.cache.event.ClearDetailsCacheEvent;
import com.oxygensened.userprofile.application.profile.dto.AddressDto;
import com.oxygensened.userprofile.application.profile.dto.request.UserDetailsRequest;
import com.oxygensened.userprofile.application.profile.dto.view.DeveloperView;
import com.oxygensened.userprofile.application.profile.dto.view.UserSearchView;
import com.oxygensened.userprofile.application.profile.dto.view.UserView;
import com.oxygensened.userprofile.application.properties.UserProfileProperties;
import com.oxygensened.userprofile.application.storage.ThumbnailOptions;
import com.oxygensened.userprofile.application.storage.ThumbnailService;
import com.oxygensened.userprofile.application.utils.JsonNullableWrapper;
import com.oxygensened.userprofile.domain.entity.Address;
import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.entity.part.AccountType;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import com.oxygensened.userprofile.domain.repository.AddressRepository;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import com.oxygensened.userprofile.domain.repository.filters.UserFilter;
import com.oxygensened.userprofile.domain.repository.filters.UserSort;
import com.oxygensened.userprofile.domain.service.DevelopersForYou;
import org.openapitools.jackson.nullable.JsonNullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.function.Consumer;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ThumbnailService thumbnailService;
    private final RequestContext requestContext;
    private final DevelopersForYou developersForYou;
    private final UserViewFactory userViewFactory;
    private final UserProfileProperties userProfileProperties;
    private final ApplicationEventPublisher applicationEventPublisher;

    public UserService(UserRepository userRepository, AddressRepository addressRepository,
                       ThumbnailService thumbnailService,
                       RequestContext requestContext, DevelopersForYou developersForYou,
                       UserViewFactory userViewFactory,
                       UserProfileProperties userProfileProperties,
                       ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.thumbnailService = thumbnailService;
        this.requestContext = requestContext;
        this.developersForYou = developersForYou;
        this.userViewFactory = userViewFactory;
        this.userProfileProperties = userProfileProperties;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public UserView getUser(Long id) {
        return userRepository.findById(id)
                             .map(userViewFactory::create)
                             .orElseThrow(() -> UserNotFoundException.withId(id));
    }

    public UserView updateUserDetails(Long id, UserDetailsRequest request) {
        var user = userRepository.findById(id)
                                 .orElseThrow(() -> UserNotFoundException.withId(id));

        if (!requestContext.isUserAuthenticated(id)) {
            LOGGER.info("User {} is not allow to update user details for different entities",
                        requestContext.userIdAsString());
            throw new AccessDeniedException();
        }

        var updatedUser = updateDetails(user, request);
        userRepository.save(updatedUser);
        applicationEventPublisher.publishEvent(ClearDetailsCacheEvent.user(id));
        return userViewFactory.create(updatedUser);
    }

    public Page<UserView> getPaginatedUsers(UserFilter filters, Pageable pageable) {
        return userRepository.findAll(pageable, filters)
                             .map(userViewFactory::create);
    }

    public void uploadThumbnail(Long id, MultipartFile file) {
        var user = userRepository.findById(id)
                                 .orElseThrow(() -> UserNotFoundException.withId(id));

        if (!requestContext.isUserAuthenticated(id)) {
            LOGGER.info("User {} is not allow to upload thumbnail for different entities",
                        requestContext.userIdAsString());
            throw new AccessDeniedException();
        }

        var mainThumbnailOptions = buildThumbnailOptions(120, 120, 0.6f);
        var smallThumbnailOptions = buildThumbnailOptions(100, 100, 0.7f);

        var mainThumbnail = thumbnailService.create(file, mainThumbnailOptions);
        var smallThumbnail = thumbnailService.create(file, smallThumbnailOptions);
        var oldMainThumbnail = user.imageName();
        var oldSmallThumbnail = user.imageNameSmall();

        user.setImageName(mainThumbnail);
        user.setImageNameSmall(smallThumbnail);
        userRepository.save(user);

        thumbnailService.delete(oldSmallThumbnail, oldMainThumbnail);
        applicationEventPublisher.publishEvent(ClearDetailsCacheEvent.thumbnail(id));
    }

    public Resource loadThumbnail(String filename) {
        return thumbnailService.load(filename);
    }

    public Resource loadThumbnailByUserId(Long id) {
        return userRepository.getThumbnail(id)
                             .map(thumbnailService::load)
                             .orElseGet(() -> thumbnailService.load(userProfileProperties.defaultThumbnail()));
    }

    public PagedListView<UserSearchView> search(String query, Pageable pageable) {
        var paginator = userRepository.search(query, pageable);
        var data = paginator.getContent().stream().map(userViewFactory::createSearchView).toList();
        return new PagedListView<>(data, (int) paginator.getTotalElements(), paginator.getNumber() + 1,
                                   paginator.getTotalPages());
    }

    public PagedListView<DeveloperView> getPaginatedDevelopers(UserFilter filter, Pageable pageable) {
        Page<DeveloperView> page;
        if (filter.sort() == UserSort.FOR_YOU) {
            if (!requestContext.isAuthorized()) {
                throw new UnauthorizedException();
            }
            if (!requestContext.hasAuthority(AccountType.PRINCIPLE.role())) {
                throw new AccessDeniedException();
            }

            var userId = requestContext.userId().get();
            page = developersForYou.getForUser(Long.valueOf(userId), filter, pageable)
                                   .map(userViewFactory::createDeveloper);
        } else {
            page = userRepository.findAll(pageable, filter)
                                 .map(userViewFactory::createDeveloper);
        }
        return new PagedListView<>(page.getContent(), (int) page.getTotalElements(), page.getNumber() + 1,
                                   page.getTotalPages());
    }

    @Transactional
    public void addRedirect(Long id) {
        userRepository.addRedirect(id);
    }


    private User updateDetails(User user, UserDetailsRequest request) {
        updateIfPresent(request.name(), user::setName);
        updateIfPresent(request.surname(), user::setSurname);
        updateIfPresent(request.phoneNumber(), user::setPhoneNumber);
        updateIfPresent(request.description(), user::setDescription);
        updateIfPresent(request.githubUrl(), user::setGithubUrl);
        updateIfPresent(request.linkedinUrl(), user::setLinkedinUrl);
        updateIfPresent(request.dateOfBirth(), user::setDateOfBirth);
        updateAddress(request.address(), user::setAddress);
        updateIfPresent(request.lookingForJob(), user::setLookingForJob);
        updateIfPresent(request.experience(), user::setExperience);
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    private void updateAddress(JsonNullable<AddressDto> addressDto, Consumer<Address> addressSetter) {
        if (JsonNullableWrapper.isPresent(addressDto)) {
            var unwrapped = JsonNullableWrapper.unwrap(addressDto);
            var address = addressRepository.findByPostCodeAndCity(unwrapped.postCode(), unwrapped.city());
            address.ifPresentOrElse(addressSetter,
                                    () -> addressSetter.accept(JsonNullableWrapper.unwrap(addressDto).toAddress()));
        }
    }

    private ThumbnailOptions buildThumbnailOptions(int height, int weight, float quality) {
        return ThumbnailOptions.builder()
                               .height(height)
                               .weight(weight)
                               .quality(quality)
                               .build();
    }
}
