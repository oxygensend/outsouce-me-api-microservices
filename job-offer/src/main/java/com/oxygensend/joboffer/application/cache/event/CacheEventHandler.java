package com.oxygensend.joboffer.application.cache.event;

import com.oxygensend.joboffer.application.cache.CacheData;
import com.oxygensend.joboffer.application.cache.CacheManagerComposite;
import java.util.List;
import java.util.Map;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
final class CacheEventHandler {
    private final CacheManagerComposite cacheManagerComposite;

    CacheEventHandler(CacheManagerComposite cacheManagerComposite) {
        this.cacheManagerComposite = cacheManagerComposite;
    }


    @EventListener(value = ClearAllCacheEvent.class)
    void handleClearAllCacheEvent(ClearAllCacheEvent event) {
        var cache = cacheManagerComposite.getCache(event.cacheName());
        if (cache == null) {
            return;
        }

        cache.clear();
    }

    @EventListener(value = ClearDetailsCacheEvent.class)
    void handleClearDetailsCacheEvent(ClearDetailsCacheEvent event) {
        var cache = cacheManagerComposite.getCache(event.cacheName());
        if (cache == null) {
            return;
        }

        CacheData.KEYS.getOrDefault(event.cacheName(), Map.of())
                      .getOrDefault(CacheData.CacheLayer.DETAILS, List.of())
                      .forEach(key -> {
                          cache.evict(key.formatted(event.keyIdentifier()));
                      });
    }

    @EventListener(value = ClearListCacheEvent.class)
    void handleClearListCacheEvent(ClearListCacheEvent event) {
        var cache = cacheManagerComposite.getCache(event.cacheName());
        if (cache == null) {
            return;
        }

        CacheData.KEYS.getOrDefault(event.cacheName(), Map.of())
                      .getOrDefault(CacheData.CacheLayer.LIST, List.of())
                      .forEach(key -> cache.clear("%s-*".formatted(key)));
    }

    @EventListener(value = ClearCacheEvent.class)
    void handleClearCacheEvent(ClearCacheEvent event) {
        var cache = cacheManagerComposite.getCache(event.cacheName());
        if (cache == null) {
            return;
        }
        CacheData.KEYS.getOrDefault(event.cacheName(), Map.of())
                      .getOrDefault(CacheData.CacheLayer.DETAILS, List.of())
                      .forEach(key -> {
                          cache.evict(key.formatted(event.detailsKeyIdentifier()));
                      });

        CacheData.KEYS.getOrDefault(event.cacheName(), Map.of())
                      .getOrDefault(CacheData.CacheLayer.LIST, List.of())
                      .stream()
                      .filter(key -> key.contains("%s"))
                      .forEach(key -> cache.clear("%s*".formatted(key.formatted(event.listPrefixIdentifier()))));
    }
}
