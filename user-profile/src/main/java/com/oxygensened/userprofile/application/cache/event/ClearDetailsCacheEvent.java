package com.oxygensened.userprofile.application.cache.event;

import com.oxygensened.userprofile.application.cache.CacheData;

/**
 * Clear details cache for provided name and identifier
 */
public record ClearDetailsCacheEvent(String cacheName, String keyIdentifier) {
    public static ClearDetailsCacheEvent user(Long keyIdentifier) {
        return new ClearDetailsCacheEvent(CacheData.USER_CACHE, keyIdentifier.toString());
    }

    public static ClearDetailsCacheEvent thumbnail(Long keyIdentifier) {
        return new ClearDetailsCacheEvent(CacheData.THUMBNAIL_CACHE, keyIdentifier.toString());
    }

}
