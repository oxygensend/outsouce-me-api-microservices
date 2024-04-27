package com.oxygensend.joboffer;

import com.oxygensend.commons_jdk.exception.ExceptionConfiguration;
import com.oxygensend.joboffer.config.properties.JobOffersProperties;
import com.oxygensend.joboffer.config.properties.ServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties( {JobOffersProperties.class, ServiceProperties.class})
@Import( {ExceptionConfiguration.class})
@SpringBootApplication
public class JobOfferApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobOfferApplication.class, args);
    }

}
