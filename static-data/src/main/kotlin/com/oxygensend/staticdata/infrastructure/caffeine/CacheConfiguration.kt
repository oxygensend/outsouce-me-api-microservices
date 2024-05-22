package com.oxygensend.staticdata.infrastructure.caffeine

import com.github.benmanes.caffeine.cache.Caffeine
import com.oxygensend.staticdata.infrastructure.StaticDataProperties
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CacheConfiguration(private val staticDataProperties: StaticDataProperties) {

    @Bean
    fun statCacheConfig(): Caffeine<Any, Any> = Caffeine.newBuilder()
        .expireAfterWrite(staticDataProperties.cache!!.statCacheTtl)


    @Bean
    fun caffeineStatCacheManager(): CacheManager = CaffeineCacheManager().apply { setCaffeine(statCacheConfig()) }

}