package com.oxygensend.joboffer.infrastructure;

import com.oxygensend.joboffer.infrastructure.services.ServiceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties( {SpringJobOffersProperties.class, ServiceProperties.class})
@Configuration
public class AppConfiguration {

}
