package com.oxygensened.userprofile.infrastructure.fixtures;

import com.oxygensend.springfixtures.Fixture;
import com.oxygensend.springfixtures.FixturesFakerProvider;
import com.oxygensened.userprofile.context.technology.TechnologyRepository;
import com.oxygensened.userprofile.domain.repository.AddressRepository;
import com.oxygensened.userprofile.domain.repository.CompanyRepository;
import com.oxygensened.userprofile.domain.repository.EducationRepository;
import com.oxygensened.userprofile.domain.repository.JobPositionRepository;
import com.oxygensened.userprofile.domain.repository.LanguageRepository;
import com.oxygensened.userprofile.domain.repository.UniversityRepository;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import com.oxygensened.userprofile.domain.service.UserIdGenerator;
import com.oxygensened.userprofile.infrastructure.services.auth.AuthClient;
import com.oxygensened.userprofile.infrastructure.services.staticdata.StaticDataClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@ConditionalOnProperty(name = "fixtures.enabled", havingValue = "true")
public class FixturesConfiguration {

    @Lazy
    @Bean
    Fixture addressFixture(StaticDataClient staticDataClient, AddressRepository addressRepository) {
        return new AddressFixture(addressRepository, staticDataClient);
    }

    @Lazy
    @Bean
    Fixture userFixture(UserRepository userRepository, AddressRepository addressRepository, TechnologyRepository technologyRepository,
                        UserIdGenerator userIdGenerator, AuthClient authClient, FixturesFakerProvider fakerProvider) {
        return new UserFixture(userRepository, addressRepository, technologyRepository, userIdGenerator, authClient, fakerProvider);
    }

    @Lazy
    @Bean
    Fixture technologyFixture(UserRepository userRepository, EducationRepository educationRepository, UniversityRepository universityRepository,
                              FixturesFakerProvider fakerProvider) {
        return new EducationFixture(userRepository, educationRepository, universityRepository, fakerProvider);
    }

    @Lazy
    @Bean
    Fixture jobPositionFixture(UserRepository userRepository, JobPositionRepository jobPositionRepository, CompanyRepository companyRepository, FixturesFakerProvider fakerProvider) {
        return new JobPositionFixture(userRepository, jobPositionRepository, companyRepository, fakerProvider);
    }

    @Lazy
    @Bean
    Fixture languageFixture(UserRepository userRepository, LanguageRepository languageRepository, FixturesFakerProvider fakerProvider) {
        return new LanguageFixture(userRepository, languageRepository, fakerProvider);
    }

    @Lazy
    @Bean
    Fixture universityFixture(UniversityRepository universityRepository, FixturesFakerProvider fakerProvider) {
        return new UniversityFixture(universityRepository, fakerProvider);
    }

    @Lazy
    @Bean
    Fixture companyFixture(CompanyRepository companyRepository, FixturesFakerProvider fakerProvider) {
        return new CompanyFixture(companyRepository, fakerProvider);
    }

}
