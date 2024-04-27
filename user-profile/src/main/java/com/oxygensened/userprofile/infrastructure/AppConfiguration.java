package com.oxygensened.userprofile.infrastructure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties( {SpringUserProfileProperties.class})
@Configuration
public class AppConfiguration {
}
