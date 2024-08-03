package com.oxygensened.userprofile.application.profile;

import com.oxygensened.userprofile.application.profile.dto.view.AddressView;
import com.oxygensened.userprofile.application.profile.dto.view.DeveloperView;
import com.oxygensened.userprofile.application.profile.dto.view.UserSearchView;
import com.oxygensened.userprofile.application.profile.dto.view.UserView;
import com.oxygensened.userprofile.application.properties.UserProfileProperties;
import com.oxygensened.userprofile.domain.UserSearchResult;
import com.oxygensened.userprofile.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserViewFactory {

    private final String thumbnailServerUrl;
    private final String defaultThumbnail;

    public UserViewFactory(UserProfileProperties userProfileProperties) {
        this.thumbnailServerUrl = userProfileProperties.thumbnailServerUrl();
        this.defaultThumbnail = userProfileProperties.defaultThumbnail();
    }

    public UserView create(User user) {
        var image = user.imageName() != null ? thumbnailServerUrl + '/' + user.imageName() :
                    thumbnailServerUrl + '/' + defaultThumbnail;
        var address = user.address() != null ? AddressView.from(user.address()) : null;
        return new UserView(user.email(), user.id().toString(), user.name(), user.surname(), user.fullName(),
                            user.phoneNumber(), user.description(),
                            user.linkedinUrl(), user.githubUrl(), user.dateOfBirth(), user.accountType(), address,
                            user.activeJobPosition(),
                            user.opinionsRate(), user.opinionsCount(), image, user.technologies().stream().toList(),
                            user.experience());
    }


    public DeveloperView createDeveloper(User user) {
        var image = user.imageNameSmall() != null ? thumbnailServerUrl + '/' + user.imageNameSmall() :
                    thumbnailServerUrl + '/' + defaultThumbnail;
        return new DeveloperView(user.id().toString(), user.fullName(), image, user.activeJobPosition(),
                                 user.description());
    }

    public UserSearchView createSearchView(UserSearchResult userSearchResult) {
        var image = userSearchResult.imagePath() != null ? thumbnailServerUrl + '/' + userSearchResult.imagePath() :
                    thumbnailServerUrl + '/' + defaultThumbnail;
        return new UserSearchView(userSearchResult.id(), userSearchResult.fullName(), image,
                                  userSearchResult.activeJobPosition());
    }
}
