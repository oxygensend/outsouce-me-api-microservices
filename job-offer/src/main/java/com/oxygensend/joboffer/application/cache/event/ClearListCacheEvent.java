package com.oxygensend.joboffer.application.cache.event;

import com.oxygensend.joboffer.application.cache.CacheData;

/**
 * Clear all list cache for provided cache name
 */
public record ClearListCacheEvent(String cacheName) {
    public final static ClearListCacheEvent JOB_OFFER = new ClearListCacheEvent(CacheData.JOB_OFFER_CACHE);
}
