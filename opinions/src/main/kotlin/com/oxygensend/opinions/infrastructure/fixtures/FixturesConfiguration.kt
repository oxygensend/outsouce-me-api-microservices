package com.oxygensend.opinions.infrastructure.fixtures

import com.oxygensend.opinions.domain.OpinionRepository
import com.oxygensend.opinions.domain.UserRepository
import com.oxygensend.springfixtures.FixturesFakerProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@ConditionalOnProperty(name = ["fixtures.enabled"], havingValue = "true")
@Configuration
class FixturesConfiguration {

    @Bean
    fun opinionFixture(
        opinionRepository: OpinionRepository,
        userRepository: UserRepository,
        applicationEventPublisher: ApplicationEventPublisher,
        fakerProvider: FixturesFakerProvider
    ): OpinionFixture = OpinionFixture(opinionRepository, userRepository, applicationEventPublisher, fakerProvider)
}