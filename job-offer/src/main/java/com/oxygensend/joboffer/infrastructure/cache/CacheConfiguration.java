package com.oxygensend.joboffer.infrastructure.cache;

import com.oxygensend.joboffer.infrastructure.cache.qualifiers.ForYouCache;
import com.oxygensend.joboffer.infrastructure.cache.qualifiers.GlobalCache;
import com.oxygensend.joboffer.infrastructure.cache.qualifiers.JobOffersCache;
import com.oxygensend.joboffer.infrastructure.cache.serialization.RedisKryoSerializer;
import com.oxygensend.joboffer.infrastructure.cache.serialization.SnappyRedisSerializer;
import java.time.Duration;
import java.util.List;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableConfigurationProperties(CacheProperties.class)
@Configuration
public class CacheConfiguration {

    private final CacheProperties cacheProperties;

    public CacheConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @ForYouCache
    @Bean
    public CacheManager forYouCacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(forYouRedisCacheConfiguration())
                .build();
    }

    @Primary
    @GlobalCache
    @Bean
    public CacheManager globalCacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(genericRedisCacheConfiguration(cacheProperties.globalCacheTtl()))
                .build();
    }

    @JobOffersCache
    @Bean
    public CacheManager jobOffersCacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(genericRedisCacheConfiguration(cacheProperties.jobOffersCacheTtl()))
                .build();
    }


    @Bean
    RedisCacheManagerComposite redisCacheManager(List<CacheManager> cacheManagers) {
        return new RedisCacheManagerComposite(cacheManagers);
    }

    @Bean
    StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    SnappyRedisSerializer<?> genericSnappyRedisSerializer() {
        return new SnappyRedisSerializer<>(new RedisKryoSerializer<>());
    }

    private RedisCacheConfiguration genericRedisCacheConfiguration(Duration ttl) {
        return RedisCacheConfiguration.defaultCacheConfig()
                                      .entryTtl(ttl)
                                      .disableCachingNullValues()
                                      .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer()))
                                      .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericSnappyRedisSerializer()));
    }


    private RedisCacheConfiguration forYouRedisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                                      .entryTtl(cacheProperties.forYouCacheTtl())
                                      .disableCachingNullValues();
    }
}
