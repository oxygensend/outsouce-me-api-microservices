package com.oxygensend.messenger.infrastructure;

import com.oxygensend.messenger.application.MessagesProperties;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "messages")
public record SpringMessagesProperties(@NotEmpty String serviceId) implements MessagesProperties {

}
