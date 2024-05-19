package com.oxygensened.userprofile.infrastructure.redis;

import org.springframework.beans.factory.annotation.Qualifier;

@Qualifier("globalCache")
public @interface GlobalCache {
}
