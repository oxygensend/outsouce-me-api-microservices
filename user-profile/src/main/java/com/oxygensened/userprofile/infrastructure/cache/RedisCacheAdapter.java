package com.oxygensened.userprofile.infrastructure.cache;

import com.oxygensened.userprofile.application.cache.CacheAdapter;
import org.springframework.data.redis.cache.RedisCache;

final class RedisCacheAdapter implements CacheAdapter {

    private final RedisCache redisCache;

    RedisCacheAdapter(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    public void clear(String keyPattern) {
        redisCache.clear(keyPattern);
    }

    @Override
    public void clear() {
        redisCache.clear();
    }

    @Override
    public void evict(String key) {
        redisCache.evict(key);
    }

}
