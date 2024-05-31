package com.oxygensend.joboffer.application.cache.event;

import com.oxygensend.joboffer.application.cache.CacheData;

/**
 * Clear details cache for provided name and identifier
 */
public record ClearDetailsCacheEvent(String cacheName, String keyIdentifier) {
    public static ClearDetailsCacheEvent jobOfferCache(String keyIdentifier) {
        return new ClearDetailsCacheEvent(CacheData.JOB_OFFER_CACHE, keyIdentifier);
    }

}
