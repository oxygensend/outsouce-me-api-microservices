package com.oxygensened.userprofile.infrastructure.elasticsearch;

import com.oxygensened.userprofile.domain.UserSearchResult;
import com.oxygensened.userprofile.domain.entity.User;

public class ElasticSearchMapper {
    private final String userThumbnailServerUrl;

    ElasticSearchMapper(String userThumbnailServerUrl) {
        this.userThumbnailServerUrl = userThumbnailServerUrl;
    }

    public UserES mapUserToUserES(User user) {
        var city = user.address() != null ? user.address().city() : null;
        return new UserES(user.id().toString(), user.fullName(), user.imageNameSmall(), user.description(), user.activeJobPosition(),
                          user.popularityOrder(), user.accountType(), user.technologies(), city);
    }

    public UserSearchResult mapUserESToUserSearchResult(UserES userES) {
        var imagePath = userES.imagePath() != null ? userThumbnailServerUrl + "/" + userES.imagePath() : null;
        return new UserSearchResult(userES.id(), userES.fullName(), imagePath, userES.activeJobPosition());
    }
}
