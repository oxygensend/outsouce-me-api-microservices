package com.oxygensend.joboffer.application.cache;

public interface CacheAdapter {

    void clear(String keyPattern);

    void clear();

    void evict(String key);

}
