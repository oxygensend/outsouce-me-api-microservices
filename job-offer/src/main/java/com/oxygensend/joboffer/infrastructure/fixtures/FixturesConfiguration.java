package com.oxygensend.joboffer.infrastructure.fixtures;

import com.oxygensend.joboffer.domain.repository.AddressRepository;
import com.oxygensend.joboffer.domain.repository.ApplicationRepository;
import com.oxygensend.joboffer.domain.repository.JobOfferRepository;
import com.oxygensend.joboffer.domain.repository.UserRepository;
import com.oxygensend.joboffer.infrastructure.services.ServiceProperties;
import com.oxygensend.springfixtures.Fixture;
import com.oxygensend.springfixtures.FixturesFakerProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestClient;

@ConditionalOnProperty(name = "fixtures.enabled", havingValue = "true")
@Configuration
public class FixturesConfiguration {

    @Lazy
    @Bean
    Fixture addressFixture(ServiceProperties serviceProperties, RestClient restClient,
                           AddressRepository addressRepository) {
        return new AddressFixture(addressRepository, restClient, serviceProperties.staticData().url());
    }

    @Lazy
    @Bean
    Fixture jobOfferFixture(FixturesFakerProvider fixturesFakerProvider, JobOfferRepository jobOfferRepository,
                            UserRepository userRepository,
                            AddressRepository addressRepository, RestClient restClient,
                            ServiceProperties serviceProperties) {
        return new JobOfferFixture(fixturesFakerProvider, jobOfferRepository, userRepository, addressRepository,
                                   restClient, serviceProperties.staticData().url());
    }

    @Lazy
    @Bean
    Fixture applicationFixture(FixturesFakerProvider fixturesFakerProvider, ApplicationRepository applicationRepository,
                               JobOfferRepository jobOfferRepository, UserRepository userRepository) {
        return new ApplicationFixture(fixturesFakerProvider, applicationRepository, jobOfferRepository, userRepository);
    }


}
