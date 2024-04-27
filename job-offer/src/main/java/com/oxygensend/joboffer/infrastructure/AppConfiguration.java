package com.oxygensend.joboffer.infrastructure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties( {SpringJobOffersProperties.class})
@Configuration
public class AppConfiguration {

}
