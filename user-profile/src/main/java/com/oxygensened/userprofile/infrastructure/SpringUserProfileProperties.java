package com.oxygensened.userprofile.infrastructure;

import com.oxygensened.userprofile.context.Topics;
import com.oxygensened.userprofile.context.UserProfileProperties;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "user-profile")
record SpringUserProfileProperties(String serviceId,
                                   Map<Topics, String> topics,
                                   @NotNull String thumbnailServerUrl) implements UserProfileProperties {
    private final static Integer TOPICS_SIZE = 1;

    public SpringUserProfileProperties {
        if (topics.size() != TOPICS_SIZE) {
            throw new IllegalArgumentException("Invalid topics configuration");
        }
    }
}
