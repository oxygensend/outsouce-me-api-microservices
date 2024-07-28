package com.oxygensend.joboffer.application.cache;

import jakarta.validation.constraints.NotNull;

import java.util.Collection;

public interface CacheManagerComposite {
    CacheAdapter getCache(@NotNull String name);
    Collection<String> getCacheNames();
}
