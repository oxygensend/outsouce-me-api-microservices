package com.oxygensend.joboffer.application.cache.event;

/**
 * Destroys cache for provided name
 */
public record ClearAllCacheEvent(String cacheName) {
}
