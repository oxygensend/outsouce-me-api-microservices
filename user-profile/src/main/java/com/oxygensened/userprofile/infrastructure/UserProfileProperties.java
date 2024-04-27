package com.oxygensened.userprofile.infrastructure;

import com.oxygensened.userprofile.infrastructure.kafka.Topics;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "user-profile")
public record UserProfileProperties(String serviceId, Map<Topics, String> topics) {
    private final static Integer TOPICS_SIZE = 1;

    public UserProfileProperties {
        if (topics.size() != TOPICS_SIZE) {
            throw new IllegalArgumentException("Invalid topics configuration");
        }
    }
}
