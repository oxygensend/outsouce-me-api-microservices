package com.oxygensend.opinions.infrastructure

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(SpringOpinionsProperties::class)
@Configuration
class AppConfiguration {

    private val applicationCoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Bean
    fun applicationScope(): CoroutineScope = applicationCoroutineScope;
}