package com.oxygensend.joboffer.application.cache.event;

import com.oxygensend.joboffer.application.cache.CacheData;

/**
 * Allow for cache clean up for provided identifiers for both details and list cache
 */
public record ClearCacheEvent(String cacheName,
                              String detailsKeyIdentifier,
                              String listPrefixIdentifier) {

    public static ClearCacheEvent jobOffer(String detailsKeyIdentifier, String listPrefixIdentifier){
        return new ClearCacheEvent(CacheData.JOB_OFFER_CACHE, detailsKeyIdentifier, listPrefixIdentifier);
    }
}
