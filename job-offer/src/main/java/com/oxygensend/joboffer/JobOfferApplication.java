package com.oxygensend.joboffer;

import com.oxygensend.commons_jdk.exception.ExceptionConfiguration;
import com.oxygensend.joboffer.infrastructure.services.ServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties( {ServiceProperties.class})
@Import( {ExceptionConfiguration.class})
@SpringBootApplication
public class JobOfferApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobOfferApplication.class, args);
    }

}
