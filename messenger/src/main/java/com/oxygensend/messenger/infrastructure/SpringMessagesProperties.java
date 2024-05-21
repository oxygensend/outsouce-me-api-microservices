package com.oxygensend.messenger.infrastructure;

import com.oxygensend.messenger.application.properties.InternalMessageProperties;
import com.oxygensend.messenger.application.properties.MessagesProperties;
import com.oxygensend.messenger.application.properties.NotificationsProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "messages")
public record SpringMessagesProperties(@NotEmpty String serviceId,
                                       @Valid SpringNotificationProperties notifications) implements MessagesProperties {
    public record SpringNotificationProperties(@NotNull
                                               @Valid
                                               SpringInternalMessageProperties mailMessageDeliveryInternal) implements NotificationsProperties {

    }

    public record SpringInternalMessageProperties(@NotEmpty String body) implements InternalMessageProperties {
    }

}
