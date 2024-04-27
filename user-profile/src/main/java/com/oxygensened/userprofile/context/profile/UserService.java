package com.oxygensened.userprofile.context.profile;

import com.oxygensened.userprofile.context.profile.dto.AddressDto;
import com.oxygensened.userprofile.context.profile.dto.request.UserDetailsRequest;
import com.oxygensened.userprofile.context.profile.dto.view.UserView;
import com.oxygensened.userprofile.context.storage.ThumbnailOptions;
import com.oxygensened.userprofile.context.storage.ThumbnailService;
import com.oxygensened.userprofile.domain.entity.Address;
import com.oxygensened.userprofile.domain.repository.AddressRepository;
import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import com.oxygensened.userprofile.context.utils.JsonNullableWrapper;
import com.oxygensened.userprofile.domain.repository.filters.UserFilters;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.oxygensened.userprofile.context.utils.Patch.updateIfPresent;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ThumbnailService thumbnailService;

    public UserService(UserRepository userRepository, AddressRepository addressRepository, ThumbnailService thumbnailService) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.thumbnailService = thumbnailService;
    }

    public UserView getUser(Long id) {
        return userRepository.findById(id)
                             .map(UserView::from)
                             .orElseThrow(() -> UserNotFoundException.withId(id));
    }

    public UserView updateUserDetails(Long id, UserDetailsRequest request) {
        var user = userRepository.findById(id)
                                 .orElseThrow(() -> UserNotFoundException.withId(id));

        var updatedUser = updateDetails(user, request);
        userRepository.save(updatedUser);
        return UserView.from(user);
    }

    public Page<UserView> getPaginatedUsers(UserFilters filters, Pageable pageable) {
        return userRepository.findAll(pageable, filters).map(UserView::from);
    }

    public void uploadThumbnail(Long id, MultipartFile file) {
        var user = userRepository.findById(id)
                                 .orElseThrow(() -> UserNotFoundException.withId(id));

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
    }

    public Resource loadThumbnail(String filename) {
        return thumbnailService.load(filename);
    }

    private User updateDetails(User user, UserDetailsRequest request) {
        updateIfPresent(request.name(), user::setName);
        updateIfPresent(request.surname(), user::setSurname);
        updateIfPresent(request.phoneNumber(), user::setPhoneNumber);
        updateIfPresent(request.description(), user::setDescription);
        updateIfPresent(request.githubUrl(), user::setGithubUrl);
        updateIfPresent(request.linkedinUrl(), user::setLinkedinUrl);
        updateIfPresent(request.dateOfBirth(), user::setDateOfBirth);
        updateAddress(request.addressDto(), user::setAddress);
        updateIfPresent(request.lookingForJob(), user::setLookingForJob);
        updateIfPresent(request.experience(), user::setExperience);
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    private void updateAddress(JsonNullable<AddressDto> addressDto, Consumer<Address> addressSetter) {
        if (JsonNullableWrapper.isPresent(addressDto)) {
            var address = addressRepository.findByPostCode(JsonNullableWrapper.unwrap(addressDto).postCode());
            address.ifPresentOrElse(addressSetter, () -> addressSetter.accept(JsonNullableWrapper.unwrap(addressDto).toAddress()));
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
