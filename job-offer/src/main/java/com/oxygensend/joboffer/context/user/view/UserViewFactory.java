package com.oxygensend.joboffer.context.user.view;

import com.oxygensend.joboffer.config.properties.JobOffersProperties;
import com.oxygensend.joboffer.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserViewFactory {
    private final String userThumbnailServerUrl;

    public UserViewFactory(JobOffersProperties properties) {
        this.userThumbnailServerUrl = properties.userThumbnailServerUrl();
    }

    public BaseUserView createBaseUserView(User user) {
        return new BaseUserView(user.id(),
                                user.fullName(),
                                user.thumbnailPath(userThumbnailServerUrl));
    }

    public UserView createUserView(User user) {
        return new UserView(user.id(),
                            user.fullName(),
                            user.thumbnailPath(userThumbnailServerUrl),
                            user.activeJobPosition());
    }

    public UserDetailsView createUserDetailsView(User user) {
        return new UserDetailsView(
                user.id(),
                user.fullName(),
                user.email(),
                user.thumbnailPath(userThumbnailServerUrl),
                user.activeJobPosition()
        );
    }
}
