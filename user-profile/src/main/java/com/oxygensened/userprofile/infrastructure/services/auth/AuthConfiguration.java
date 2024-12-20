package com.oxygensened.userprofile.infrastructure.services.auth;

import com.oxygensend.commonspring.feign.ClientFactory;
import com.oxygensened.userprofile.infrastructure.services.ServiceProperties;
import com.oxygensened.userprofile.application.auth.AuthRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ServiceProperties.class)
public class AuthConfiguration {

    @Bean
    AuthClient authClient(ClientFactory clientFactory, ServiceProperties serviceProperties) {
        return clientFactory.create(serviceProperties.auth().url(), AuthClient.class, builder -> {
        });
    }

    @Bean
    AuthRepository authRepository(AuthClient authClient) {
        return new AuthRestRepository(authClient);
    }
}
