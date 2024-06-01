package com.oxygensened.userprofile.application.cache;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;

public interface CacheManagerComposite {
    CacheAdapter getCache(@NotNull String name);
    Collection<String> getCacheNames();
}
