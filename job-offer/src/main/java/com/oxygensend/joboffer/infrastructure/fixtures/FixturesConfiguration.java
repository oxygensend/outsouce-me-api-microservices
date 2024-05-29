package com.oxygensend.joboffer.infrastructure.fixtures;

import com.oxygensend.joboffer.application.technology.TechnologyRepository;
import com.oxygensend.joboffer.domain.repository.AddressRepository;
import com.oxygensend.joboffer.domain.repository.ApplicationRepository;
import com.oxygensend.joboffer.domain.repository.JobOfferRepository;
import com.oxygensend.joboffer.domain.repository.UserRepository;
import com.oxygensend.joboffer.infrastructure.services.staticdata.StaticDataClient;
import com.oxygensend.springfixtures.Fixture;
import com.oxygensend.springfixtures.FixturesFakerProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@ConditionalOnProperty(name = "fixtures.enabled", havingValue = "true")
@Configuration
public class FixturesConfiguration {

    @Lazy
    @Bean
    Fixture addressFixture(StaticDataClient staticDataClient, AddressRepository addressRepository) {
        return new AddressFixture(staticDataClient, addressRepository);
    }

    @Lazy
    @Bean
    Fixture jobOfferFixture(FixturesFakerProvider fixturesFakerProvider, JobOfferRepository jobOfferRepository, UserRepository userRepository,
                                    AddressRepository addressRepository, TechnologyRepository technologyRepository) {
        return new JobOfferFixture(fixturesFakerProvider, jobOfferRepository, userRepository, addressRepository, technologyRepository);
    }

    @Lazy
    @Bean
    Fixture applicationFixture(FixturesFakerProvider fixturesFakerProvider, ApplicationRepository applicationRepository,
                               JobOfferRepository jobOfferRepository, UserRepository userRepository) {
        return new ApplicationFixture(fixturesFakerProvider, applicationRepository, jobOfferRepository, userRepository);
    }


}
