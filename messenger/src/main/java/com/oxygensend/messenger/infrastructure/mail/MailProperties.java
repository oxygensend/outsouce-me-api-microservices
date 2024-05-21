package com.oxygensend.messenger.infrastructure.mail;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "messages.mail")
public record MailProperties(@NotEmpty String host,
                             @NotNull int port,
                             String username,
                             String password,
                             @NotEmpty String protocol,
                             String auth,
                             Boolean starttlsEnable,
                             Boolean debug,
                             Boolean testConnection) {
}

