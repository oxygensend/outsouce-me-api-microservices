package com.oxygensend.joboffer.infrastructure.cache;

import com.oxygensend.joboffer.application.cache.CacheAdapter;
import com.oxygensend.joboffer.application.cache.CacheManagerComposite;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.List;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCache;

final class RedisCacheManagerComposite implements CacheManagerComposite {
    private final List<CacheManager> cacheManagers;

    RedisCacheManagerComposite(List<CacheManager> cacheManagers) {
        this.cacheManagers = cacheManagers;
    }

    @Override
    public CacheAdapter getCache(@NotNull String name) {
        return cacheManagers.stream()
                            .filter(cacheManager -> cacheManager.getCache(name) != null)
                            .findFirst()
                            .map(cacheManager -> new RedisCacheAdapter((RedisCache) cacheManager.getCache(name)))
                            .orElse(null);
    }

    @NotNull
    @Override
    public Collection<String> getCacheNames() {
        return cacheManagers.stream()
                            .map(CacheManager::getCacheNames)
                            .flatMap(Collection::stream)
                            .toList();
    }
}
