package com.oxygensened.userprofile.infrastructure.cache;

import com.oxygensened.userprofile.application.cache.CacheManagerComposite;
import com.oxygensened.userprofile.infrastructure.cache.qualifiers.DevelopersCache;
import com.oxygensened.userprofile.infrastructure.cache.qualifiers.ForYouCache;
import com.oxygensened.userprofile.infrastructure.cache.qualifiers.GlobalCache;
import com.oxygensened.userprofile.infrastructure.cache.serialization.RedisKryoSerializer;
import com.oxygensened.userprofile.infrastructure.cache.serialization.SnappyRedisSerializer;
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

    @Bean
    CacheManagerComposite redisCacheManagerComposite(List<CacheManager> cacheManagers) {
        return new RedisCacheManagerComposite(cacheManagers);
    }

    @ForYouCache
    @Bean
    CacheManager forYouCacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(forYouRedisCacheConfiguration())
                .build();
    }

    @GlobalCache
    @Primary
    @Bean
    CacheManager globalCacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(genericRedisCacheConfiguration(cacheProperties.globalCacheTtl()))
                .build();
    }

    @DevelopersCache
    @Bean
    CacheManager developersCacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(genericRedisCacheConfiguration(cacheProperties.developersCacheTtl()))
                .build();
    }

    @Bean
    StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    SnappyRedisSerializer<?> genericSnappyRedisSerializer() {
        return new SnappyRedisSerializer<>(new RedisKryoSerializer<>());
    }

    RedisCacheConfiguration genericRedisCacheConfiguration(Duration ttl) {
        return RedisCacheConfiguration.defaultCacheConfig()
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
