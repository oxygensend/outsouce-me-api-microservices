package com.oxygensened.userprofile.infrastructure.services.joboffers;

import com.oxygensend.commonspring.feign.ClientFactory;
import com.oxygensened.userprofile.domain.repository.JobOfferRepository;
import com.oxygensened.userprofile.infrastructure.services.ServiceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobOfferConfiguration {

    @Bean
    JobOfferClient jobOfferClient(ClientFactory clientFactory, ServiceProperties serviceProperties) {
        return clientFactory.create(serviceProperties.jobOffers().url(), JobOfferClient.class, builder -> {
        });
    }

    @Bean
    JobOfferRepository jobOfferRestRepository(JobOfferClient jobOfferClient){
        return new JobOfferRestRepository(jobOfferClient);
    }

}
