package com.oxygensened.userprofile.context.profile;

import com.oxygensened.userprofile.context.properties.UserProfileProperties;
import com.oxygensened.userprofile.context.profile.dto.view.AddressView;
import com.oxygensened.userprofile.context.profile.dto.view.DeveloperView;
import com.oxygensened.userprofile.context.profile.dto.view.UserView;
import com.oxygensened.userprofile.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserViewFactory {

    private final String thumbnailServerUrl;

    public UserViewFactory(UserProfileProperties userProfileProperties) {
        this.thumbnailServerUrl = userProfileProperties.thumbnailServerUrl();
    }

    public UserView create(User user) {
        var image = user.imageName() != null ? thumbnailServerUrl + '/' + user.imageName() : null;
        var address = user.address() != null ? AddressView.from(user.address()) : null;
        return new UserView(user.email(), user.id().toString(), user.name(), user.surname(), user.fullName(), user.phoneNumber(), user.description(),
                            user.linkedinUrl(), user.githubUrl(), user.dateOfBirth(), user.accountType(), address, user.activeJobPosition(),
                            user.opinionsRate(), user.opinionsCount(), image, user.technologies().stream().toList(), user.experience());
    }


    public DeveloperView createDeveloper(User user) {
        var image = user.imageNameSmall() != null ? thumbnailServerUrl + '/' + user.imageNameSmall() : null;
        return new DeveloperView(user.id().toString(), user.fullName(), image, user.activeJobPosition(), user.description());
    }
}
