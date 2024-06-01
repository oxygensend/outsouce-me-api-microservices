package com.oxygensened.userprofile.application.cache.event;


import com.oxygensened.userprofile.application.cache.CacheData;

/**
 * Allow for cache clean up for provided identifiers for both details and list cache
 */
public record ClearCacheEvent(String cacheName,
                              String detailsKeyIdentifier,
                              String listPrefixIdentifier) {

    public static ClearCacheEvent education(Long listPrefixIdentifier) {
        return new ClearCacheEvent(CacheData.EDUCATION_CACHE, null, listPrefixIdentifier.toString());
    }

    public static ClearCacheEvent language(Long listPrefixIdentifier) {
        return new ClearCacheEvent(CacheData.LANGUAGE_CACHE, null, listPrefixIdentifier.toString());
    }

    public static ClearCacheEvent jobPosition(Long listPrefixIdentifier) {
        return new ClearCacheEvent(CacheData.JOB_POSITION_CACHE, null, listPrefixIdentifier.toString());
    }
}
