package com.oxygensened.userprofile.context.profile;

import com.oxygensened.userprofile.context.profile.dto.AddressDto;
import com.oxygensened.userprofile.context.profile.dto.request.UserDetailsRequest;
import com.oxygensened.userprofile.context.profile.dto.view.UserView;
import com.oxygensened.userprofile.domain.Address;
import com.oxygensened.userprofile.domain.AddressRepository;
import com.oxygensened.userprofile.domain.User;
import com.oxygensened.userprofile.domain.UserRepository;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import com.oxygensened.userprofile.infrastructure.jackson.JsonNullableWrapper;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.oxygensened.userprofile.context.utils.Patch.updateIfPresent;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public UserService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
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

    public Page<UserView> getPaginatedUsers(UserFilters filters, Pageable pageable) {
        return userRepository.findAll(pageable, filters).map(UserView::from);
    }
}
