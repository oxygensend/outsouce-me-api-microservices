package com.oxygensened.userprofile.application.cache.event;


import com.oxygensened.userprofile.application.cache.CacheData;

/**
 * Clear all list cache for provided cache name
 */
public record ClearListCacheEvent(String cacheName) {
    public static final ClearListCacheEvent DEVELOPERS = new ClearListCacheEvent(CacheData.DEVELOPERS_KEY);
}
